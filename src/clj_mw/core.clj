(ns clj-mw.core
  (:gen-class)
  (:require [clj-mw.api :as api]
            [clj-mw.asyncapi :as aapi]))

(defn -main
  [& args]
  (api/login)
  (def edit-token (api/get-token))
  (def prom (aapi/edit-replace "TestAsync" {:content "Async testing again 2" :token edit-token}))
  (println (:body @prom)))
