(ns kafka-producer.util-test
  (:require [clojure.test :refer :all]
            [kafka-producer.util :refer :all]))

(deftest test-get-trans-type
  (is (= (get-trans-type "debit") :debit))
  (is (= (get-trans-type "credit") :credit))
  (is (= (get-trans-type "credits") nil)))

(def account-pre
  {:id     901
   :number 100010059
   :type   "CC"})

(def transaction-pre
  {:id      1001
   :amount  40
   :type    "credit"
   :account account-pre})

(def transaction-post
  {:id      1001
   :amount  40
   :type    :credit
   :account account-pre})

(deftest test-construct
  (let [current-time (.toString (java.time.LocalDateTime/now))
        transaction (construct transaction-pre current-time)]
    (is (= (get transaction :id) (get transaction-post :id)))
    (is (= (get transaction :amount) (get transaction-post :amount)))
    (is (= (get transaction :type) (get transaction-post :type)))
    (is (= (get transaction :account) (get transaction-post :account)))
    (is (= (get transaction :time) current-time))
    (is (= (get transaction-post :time) nil))
    )
  )
