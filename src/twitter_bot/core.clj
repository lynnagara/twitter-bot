(ns twitter-bot.core
  (:use
   [twitter.oauth]
   [twitter.callbacks]
   [twitter.callbacks.handlers]
   [twitter.api.restful])
  (:import
   (twitter.callbacks.protocols SyncSingleCallback))
  (:require [clojure.edn :as edn]))


(def my-creds 
  (let [credentials (edn/read-string (slurp "data/credentials.edn"))]
    (make-oauth-creds (credentials :app-consumer-key)
                      (credentials :app-consumer-secret)
                      (credentials :user-access-token)
                      (credentials :user-access-token-secret))))

(defn replace-user-mention [text, user-mention]
  (clojure.string/replace text (re-pattern (str "@" (user-mention :screen_name))) (user-mention :name)))

(defn replace-user-mentions [tweet]
  (reduce replace-user-mention (tweet :text) ((tweet :entities) :user_mentions)))

(defn strip-urls [text]
  (clojure.string/replace text (re-pattern "https?:\\/\\/[a-zA-Z0-9./?=_-]+") ""))

(defn strip-leading-dot-rt-trim [text]
  (.trim (clojure.string/replace text (re-pattern "^(RT |\\.)") "")))

(defn sanitize-tweet [tweet]
  (strip-leading-dot-rt-trim (strip-urls (replace-user-mentions tweet))))


;; TODO: For every group of three words, we'll sort them so we can later look up
;; the first two and find the third. For example -
;; "Once upon" => "a"
;; "upon a" => "time"

;; We should also keep track of common starting pairs of words

(defn insert-into-dictionary [text]
  (let [words (clojure.string/split text #" ")]
    (println text)))

(defn process-tweet [tweet]
  (insert-into-dictionary (sanitize-tweet tweet)))

(defn get-user-tweets [screen-name]
  (println (str "Fetching @" screen-name " tweets"))
  (doseq [tweet ((statuses-user-timeline
                  :oauth-creds my-creds
                  :params {:screen-name screen-name :exclude-replies "true"})
                  :body)]
          (process-tweet tweet)))

(defn -main
  "I generate wonderful tweets."
  [& args]
  (println "Hello, World!")
  (get-user-tweets "lynnagara"))
