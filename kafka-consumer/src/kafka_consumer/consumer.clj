(ns kafka-consumer.consumer
  (:gen-class)
  (:require
    [dotenv :refer [env]]
    [clojure.tools.logging :as log]
    [deercreeklabs.lancaster :as l])
  (:import
    (java.time Duration)
    (org.apache.kafka.clients.consumer ConsumerConfig KafkaConsumer)))

(l/def-enum-schema trans-type-schema
                   :credit :debit)

(l/def-record-schema account-schema
                     [:id l/int-schema]
                     [:number l/int-schema]
                     [:type l/string-schema]
                     [:state l/string-schema])


(l/def-record-schema transaction-schema
                     [:id l/int-schema]
                     [:amount l/int-schema]
                     [:currency l/string-schema]
                     [:type trans-type-schema]
                     [:time l/string-schema]
                     [:status l/string-schema]
                     [:account account-schema])

(def -build-consumer ^KafkaConsumer
  ;"Create the kafka consumer to receive messages"
  ;[bootstrap-server]
  (let [consumer-props {
                        ConsumerConfig/GROUP_ID_CONFIG                 "clojure_demo_group"
                        ConsumerConfig/KEY_DESERIALIZER_CLASS_CONFIG   "org.apache.kafka.common.serialization.StringDeserializer"
                        ConsumerConfig/VALUE_DESERIALIZER_CLASS_CONFIG "io.confluent.kafka.serializers.KafkaAvroDeserializer"
                        ConsumerConfig/BOOTSTRAP_SERVERS_CONFIG        (env :BOOTSTRAP_SERVER)
                        "schema.registry.url"                          (env :SCHEMA_REGISTRY_URL)}]
    (log/info "building the kafka consumer")
    (KafkaConsumer. consumer-props)))

(defn consumer! [topic]
  (with-open [consumer -build-consumer]
    (.subscribe consumer [topic])
    (loop [tc 0
           records []]
      (let [new-tc (reduce
                     (fn [tc record]
                       (log/info "\nReceived " (str "the processed value: \n" (l/deserialize transaction-schema transaction-schema (.value record)))))
                     tc
                     records)]
        (log/debugf "Waiting for message in topic: %s" topic)
        (recur new-tc
               (seq (.poll consumer (Duration/ofSeconds 1))))))))

(defn -main [& args]
  (apply consumer! args))
