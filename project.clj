(defproject twitter-bot "0.1.0-SNAPSHOT"
  :description "Twitter bot"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.codec "0.1.0"]
                 [clj-http "2.1.0"]
                 [twitter-api "0.7.8"]]
  :main ^:skip-aot twitter-bot.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
