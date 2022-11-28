(ns kafka-producer.router-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [kafka-producer.routes :refer :all]
            [ring.mock.request :as mock]))

(deftest test-echo-route
  (is (= (echo-route (mock/request :get "/hello"))
         {:status  200
          :headers {"Content-Type" "text/plain"}
          :body    "/hello"})))

(def transaction-pre
  {:id     1001
   :amount 40
   :type   "credit"})

(deftest test-add-transation
  (is (= (add-transaction-route (-> (mock/request :post "/transactions/test-topic")
                                    (mock/json-body transaction-pre)))
         {:status  200
          :headers {"Content-Type" "application/json"}
          :body    nil})))

(deftest test-add-transation-default-topic
  (is (= (add-transaction-route (-> (mock/request :post "/transactions")
                                    (mock/json-body transaction-pre)))
         {:status  200
          :headers {"Content-Type" "application/json"}
          :body    nil})))