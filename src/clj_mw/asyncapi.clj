(ns clj-mw.asyncapi
  (:require [clj-mw.http :as http]
            [clj-mw.config :refer [config]]))

(defn edit
  "Makes a raw edit request
  This function makes a raw edit request to the async http functions. This
  function then returns a promise that can be resolved and dereferenced
  later with the @ or (deref) syntax.
  The hashmap `query` contains a full querystring in a mapped form."
  [query-map]
  (def prom (promise))
  (def success (fn [res] (deliver prom res)))
  (def failure (fn [rej] (deliver prom rej)))
  (http/http-async-post query-map config success failure)
  prom)

;; FIXME: Add error checking for the login endpoint.
;;        Currently this function makes no effort to check whether or not the
;;        attempt to login was successful or if there were any other errors
;;        that were raised in the process. This function does return the API
;;        response so if needed error checking can be done by capturing the
;;        result and testing it for errors (ss 6/10/20).
(defn edit-replace
  "Replaces the content of a page.
  This function in will completely replace the content of the page.
  For functions that just append or prepend to the page see:
  [[edit-append]] and [[edit-prepend]]
  The string `page-name` is the title of the destination page.
  The hashmap `options` contains the `content` to replace with and
  the `token` to use to make the request."
  [page-name options]
  (def query-map {:action "edit"
                  :title page-name
                  :text (:content options)
                  :token (:token options)
                  :format "json"})
  (edit query-map))

;; FIXME: Add error checking for the login endpoint.
;;        Currently this function makes no effort to check whether or not the
;;        attempt to login was successful or if there were any other errors
;;        that were raised in the process. This function does return the API
;;        response so if needed error checking can be done by capturing the
;;        result and testing it for errors (ss 6/10/20).
(defn edit-prepend
  "Prepends to the content of a page.
  This function in will prepend to the content of the page.
  For functions that just append to or replace the page see:
  [[edit-append]] and [[edit-replace]]
  The string `page-name` is the title of the destination page.
  The hashmap `options` contains the `content` to prepend and
  the `token` to use to make the request."
  [page-name options]
  (def query-map {:action "edit"
                  :title page-name
                  :prependtext (:content options)
                  :token (:token options)
                  :format "json"})
  (edit query-map))

;; FIXME: Add error checking for the login endpoint.
;;        Currently this function makes no effort to check whether or not the
;;        attempt to login was successful or if there were any other errors
;;        that were raised in the process. This function does return the API
;;        response so if needed error checking can be done by capturing the
;;        result and testing it for errors (ss 6/10/20).
(defn edit-append
  "Appends to the content of a page.
  This function in will Append to the content of the page.
  For functions that just prepend to or replace the page see:
  [[edit-prepend]] and [[edit-replace]]
  The string `page-name` is the title of the destination page.
  The hashmap `options` contains the `content` to append and
  the `token` to use to make the request."
  [page-name options]
  (def query-map {:action "edit"
                  :title page-name
                  :appendtext (:content options)
                  :token (:token options)
                  :format "json"})
  (edit query-map))
