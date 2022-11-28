(ns kafka-producer.api
  (:require
    [compojure.core :refer :all]
    [kafka-producer.producer :as producer]
    [kafka-producer.util :as util]
    [deercreeklabs.lancaster :as l]
    [dotenv :refer [env]]
    [clojure.tools.logging :as log])
  (:gen-class)
  (:import (java.time LocalDateTime)))

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

(defn add-transaction
  "Add a transaction"
  [record in-topic]
  (log/debug "Topic from url: " in-topic)
  (log/debug "Adding a transaction record: " record)
  (let [key (get record :type)
        message (l/serialize transaction-schema (util/construct record (.toString (LocalDateTime/now))))
        topic (or in-topic (env :DEFAULT_TOPIC))]
    (producer/send-msg key message topic)
    )
  )