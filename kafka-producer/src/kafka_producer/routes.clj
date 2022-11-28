(ns kafka-producer.routes
  (:require
    [compojure.core :refer :all]
    [kafka-producer.api :as api])
  (:gen-class))

(defn echo-route
  "Echo back the request"
  [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (-> (req :uri))})

(defn add-transaction-route
  "Endpoint for adding a transaction"
  [req]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (-> (api/add-transaction (req :params) (get-in req [:route-params :topic])))})
