(ns kafka-producer.routes
  (:require
    [compojure.core :refer :all]
    [kafka-producer.api :as api])
  (:gen-class))

(defn echo-route
  "Echo back the request"
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (-> (str "GET '/' " req))})

(defn add-transaction-route
  "Endpoint for adding a transaction"
  [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (-> (api/add-transaction (req :params)))})
