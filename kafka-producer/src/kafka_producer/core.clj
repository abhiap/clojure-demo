(ns kafka-producer.core
  (:require
    [clojure.tools.logging :as log]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [dotenv :refer [env]]
    [kafka-producer.routes :as routes]
    [org.httpkit.server :as server]
    [ring.middleware.defaults :refer :all]
    [ring.middleware.json :as js])
  (:gen-class))

(defroutes app-routes
           (GET "/" [] routes/echo-route)
           (POST "/transactions" [] routes/add-transaction-route)
           (POST "/transactions/:topic" [] routes/add-transaction-route)
           (route/not-found "Error, page not found!"))

(defn -main
  ;"Production"
  [& args]
  ;(producer/configure-kafka "localhost:9092")
  (let [port (Integer/parseInt (env :PORT))]
    (server/run-server (js/wrap-json-params (js/wrap-json-response (wrap-defaults #'app-routes api-defaults))) {:port port})
    (log/info (str "Running webserver at http:/127.0.0.1:" port "/"))
    ))
