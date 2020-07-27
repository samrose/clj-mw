(ns clj-mw.core
  (:gen-class)
  (:require [clj-mw.api :as api]
            [clj-mw.asyncapi :as aapi]))

(defn -main
  [& args]
  (api/login)
  (def edit-token (api/get-token))
  (def prom (aapi/edit-replace "TestAsync" {:content "Async testing again" :token edit-token}))
  (println (:body @prom)))
  ;(println (:tokens @edit-token)))

(def ^:dynamic *verbose* false)

(defmacro printfv
  [fmt & args]
  `(when *verbose*
     (printf ~fmt ~@args)))
(defmacro with-verbose
  [& body]
  `(binding [*verbose* true] ~@body))
