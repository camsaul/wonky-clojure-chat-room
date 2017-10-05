(defproject potchat "0.1.0-SNAPSHOT"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [amalloy/ring-gzip-middleware "0.1.3"]
                 [compojure "1.6.0"]
                 [environ "1.1.0"]
                 [hiccup "1.0.5"]
                 [ring/ring-core "1.6.2"]
                 [ring/ring-jetty-adapter "1.6.2"]]
  :plugins [[lein-environ "1.1.0"]
            [lein-ring "0.11.0"
             :exclusions [org.clojure/clojure]]]
  :ring {:handler potchat.handler/app
         :init potchat.core/init!}
  :main ^:skip-aot potchat.core
  :target-path "target/%s"
  :uberjar-name "potchat.jar"
  :profiles {:uberjar {:aot :all}})
