(ns clj-mw.core
  (:gen-class)
  (:require [clj-mw.api :as api]
            [clj-mw.asyncapi :as aapi]))
;TODO this file is not used and lives here as an example of how to use the library in another project
(defn -main
  [& args]
  (api/login)
  (def edit-token (api/get-token))
  (def prom (aapi/edit-replace "TestAsync" {:content "Async testing 123" :token edit-token}))
  (println (:body @prom)))
