(ns potchat.handler
  (:require [clojure.string :as str]
            [compojure.core :as compojure :refer [GET ANY]]
            [hiccup.page :as page]
            [ring.middleware
             #_[cookies :refer [wrap-cookies]]
             [gzip :refer [wrap-gzip]]
             #_[json :refer [wrap-json-body wrap-json-params wrap-json-response]]
             [keyword-params :refer [wrap-keyword-params]]
             [params :refer [wrap-params]]]
            [ring.util.response :as resp]))

(def ^:private messages
  (atom ["Wow!" "Cool"]))

(defn- post-message! [message]
  (swap! messages #(cons message (take 99 %))))

(defn- render-messages []
  (vec
   (cons
    :div#messages
    (for [message (reverse (take 100 @messages))]
      [:p message]))))

(defn- render-new-message-form []
  [:form {:action "/", :method :post}
   [:input {:type :text, :name :message, :placeholder "Say something"}]
   [:input {:type :submit, :value "Post"}]])

(defn- index [{{:keys [message]} :params}]
  (when (seq message)
    (post-message! message))
  (page/html5
   [:head
    [:title "PotChat 1.0"]
    [:meta {:charset "utf-8"}]
    (page/include-js "/resources/potchat.js")]
   [:body
    [:div
     [:h1 "PotChat 1.0"]]
    (render-messages)
    (render-new-message-form)]))

(defn- fetch-messages [_]
  {:status  200
   :headers {"Content-Type" "text"}
   :body    (str/join "\n" (reverse (take 100 @messages)))})

(def ^:private routes
  (compojure/routes
   (GET "/resources/potchat.js" []
        (assoc-in (resp/resource-response "potchat.js")
                  [:headers "Content-Type"] "application/javascript; charset=utf-8"))
   (GET "/messages" [] fetch-messages)
   (ANY "/" [] index)))

(def app
  "Core Ring app."
  (-> routes
      #_(wrap-json-body ; extracts json POST body and makes it avaliable on request
       {:keywords? true})
      #_wrap-json-response ; middleware to automatically serialize suitable objects as JSON in responses
      #_wrap-json-params   ; add JSON body params to normal params list
      wrap-keyword-params ; converts string keys in :params to keyword keys
      wrap-params ; parses GET and POST params as :query-params/:form-params and both as :params
      #_wrap-cookies ; Parses cookies in the request map and assocs as :cookies
      wrap-gzip))
