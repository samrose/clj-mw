(defproject clj-mw "0.1.0-SNAPSHOT"
  :description "clojure based mediawiki library based on work from https://github.com/StargazeSparkle/ibby"
  :url "http://example.com/FIXME"
  :license {:name "GPL-3.0"
            :url "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/data.json "1.0.0"]
                 [clj-http "3.10.1"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
