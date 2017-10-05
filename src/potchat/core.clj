(ns potchat.core
  (:gen-class)
  (:require [environ.core :refer [env]]
            [potchat.handler :as handler]
            [ring.adapter.jetty :as ring-jetty]))

(def ^:private jetty-instance
  (atom nil))

(def ^:private jetty-port
  (Integer/parseInt (or (env :port) "3000")))

(defn- start-jetty! []
  (when-not @jetty-instance
    (reset! jetty-instance (ring-jetty/run-jetty handler/app {:join? false, :port jetty-port}))))

(defn -main [& args]
  (start-jetty!))
