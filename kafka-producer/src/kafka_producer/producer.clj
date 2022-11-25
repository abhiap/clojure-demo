(ns kafka-producer.producer
  (:gen-class)
  (:require
    [clojure.data.json :as json]
    [dotenv :refer [env]]
    [clojure.tools.logging :as log])
  (:import
    (org.apache.kafka.clients.admin AdminClient)
    (org.apache.kafka.clients.producer Callback KafkaProducer ProducerConfig ProducerRecord)))

(def -build-producer ^KafkaProducer
  ;"Create the kafka producer to send on messages received"
  ;[bootstrap-server]
  (let [producer-props {
                        ProducerConfig/VALUE_SERIALIZER_CLASS_CONFIG "io.confluent.kafka.serializers.KafkaAvroSerializer"
                        ProducerConfig/KEY_SERIALIZER_CLASS_CONFIG   "org.apache.kafka.common.serialization.StringSerializer"
                        ProducerConfig/BOOTSTRAP_SERVERS_CONFIG      (env :BOOTSTRAP_SERVER)
                        "schema.registry.url"                        (env :SCHEMA_REGISTRY_URL)}]
    (log/debug "creating producer with config:")
    (log/debug producer-props)
    (KafkaProducer. producer-props)))

(defn create-topics!
  "Create the topic "
  [bootstrap-server topics ^Integer partitions ^Short replication]
  (let [config {"bootstrap.servers" bootstrap-server}
        adminClient (AdminClient/create config)
        new-topics (map (fn [^String topic-name] (NewTopic. topic-name partitions replication)) topics)]
    (.createTopics adminClient new-topics)))

(defn send [key message topic]
  (let [producer -build-producer
        print-ex (comp print (partial str "Failed to deliver message: "))
        print-metadata #(log/debugf "Sent record to topic %s partition [%d] @ offest %d"
                                    (.topic %)
                                    (.partition %)
                                    (.offset %))
        create-msg #(let [k key
                          v %]
                      (log/debugf "Producing record: %s\t%s" k v)
                      (ProducerRecord. topic k v))]

    (create-topics! (env :BOOTSTRAP_SERVER) [topic] 3 1)

    (let [;; We can use callbacks to handle the result of a send, like this:
          callback (reify Callback
                     (onCompletion [this metadata exception]
                       (if exception
                         (print-ex exception)
                         (print-metadata metadata)
                         )))]

      (.send producer (create-msg message) callback)
      (.flush producer)
      )
    (log/debugf "Message produced to topic: %s!" topic)))
