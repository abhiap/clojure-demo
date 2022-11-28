(ns kafka-producer.util
  (:gen-class))

(defn get-trans-type [in]
  (if (= in "credit") :credit
                      (if (= in "debit") :debit))
  )

(defn construct [record time]
  {:id       (get record :id)
   :amount   (get record :amount)
   :currency (get record :currency)
   :type     (get-trans-type (get record :type))
   :time     time
   :status   (get record :status)
   :account  (get record :account)}
  )