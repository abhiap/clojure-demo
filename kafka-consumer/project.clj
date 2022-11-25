(defproject kafka-consumer "0.1.0-SNAPSHOT"
  :description "A simple kafka consumer along with Kafka scheme registry which listens to events in a Kafka topic"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.clojure/data.json "2.4.0"]
                 [org.clojure/tools.logging "1.2.4"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [io.confluent/kafka-avro-serializer "7.3.0"]
                 [org.apache.kafka/kafka-clients "3.3.1"]
                 [deercreeklabs/lancaster "0.9.22"]
                 [lynxeyes/dotenv "1.0.2"]]
  ;:repl-options {:init-ns kafka-consumer.core}
  :repositories [["io.confluent" "https://packages.confluent.io/maven/"]]
  :main ^:skip-aot kafka-consumer.consumer
  :aliases {"consumer" ["run" "-m" "kafka-consumer.consumer"]}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
