(ns kafka-producer.api
  (:require
    [compojure.core :refer :all]
    [kafka-producer.producer :as producer]
    [deercreeklabs.lancaster :as l]
    [dotenv :refer [env]]
    [clojure.tools.logging :as log])
  (:gen-class))

(l/def-record-schema transaction-schema
                     [:id l/int-schema]
                     [:amount l/int-schema]
                     [:type l/string-schema])

(defn add-transaction
  "Add a transaction"
  [{id :id amount :amount type :type :as record}]
  (log/debug "Adding a transaction record: " record)
  (producer/send type (l/serialize transaction-schema record) (env :DEFAULT_TOPIC))
  )