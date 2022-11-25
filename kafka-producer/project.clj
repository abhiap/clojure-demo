(defproject kafka-producer "0.1.0-SNAPSHOT"
  :description "A Kafka producer app along with Kafka scheme registry and a POST api to send a message to a Kafka topic"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.apache.kafka/kafka-clients "3.3.1"]
                 [io.confluent/kafka-avro-serializer "7.3.0"]
                 [deercreeklabs/lancaster "0.9.22"]
                 [org.clojure/tools.logging "1.2.4"]
                 [ch.qos.logback/logback-classic "1.1.3"]
                 [compojure "1.6.3"]
                 [http-kit "2.6.0"]
                 [ring/ring-defaults "0.3.4"]
                 [ring/ring-devel "1.9.6"]
                 [ring/ring-json "0.5.1"]
                 [org.clojure/data.json "2.4.0"]
                 [lynxeyes/dotenv "1.0.2"]]
  :repositories [["io.confluent" "https://packages.confluent.io/maven/"]]
  :main ^:skip-aot kafka-producer.core
  :aliases {"producer" ["run" "-m" "kafka-producer.producer"]}
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
