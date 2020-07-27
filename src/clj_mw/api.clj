(ns clj-mw.api
  (:require [clojure.data.json :as json]
            [clj-mw.config :refer [config]]
            [clj-mw.http :as http]))

;; FIXME: Add error checking for the login endpoint.
;;        Currently this function makes no effort to check whether or not the
;;        attempt to login was successful or if there were any other errors
;;        that were raised in the process. This function does return the API
;;        response so if needed error checking can be done by capturing the
;;        result and testing it for errors (ss 6/10/20).
(defn login
  "Performs a login action.
  This function accesses the MediaWiki login endpoint twice. The first time
  that it does so is to obtain a token which is needed to successfully login.
  The second time it accessess the endpoint is to authenticate using both the
  login credentials as well as the CSRF token."
  []
  (def query-map {:action "login"
                  :lgname (:username config)
                  :lgpassword (:password config)
                  :format "json"})
  (def response (http/http-post query-map config))
  (def lgtoken (get-in response ["login" "token"]))
  (def login-with-token (assoc-in query-map [:lgtoken] lgtoken))
  (http/http-post login-with-token config))

;; FIXME: Gather all other tokens
;;        Currently this function does not make an effort to retrieve the
;;        various other tokens that MediaWiki requires for other types of
;;        operations (ss 6/10/20).
(defn get-token
  "Fetches tokens from the tokens endpoint.
   This function makes use of the tokens endpoint on MediaWiki sites in order
   to fetch a CSRF token that can be used to make edits to pages among other
   things."
  []
  (def query-map {:action "query"
                  :meta "tokens"
                  :format "json"})
  (def response (http/http-get query-map config))
  (get-in response ["query" "tokens" "csrftoken"]))

;; FIXME: This function currently does no error handling to discern whether or
;;        not the edit failed (ss 6/10/20).
(defn edit-replace
  "Replaces the content of a page.
  This function in will completely replace the content of the page.
  For functions that just append or prepend to the page see:
  [[edit-append]] and [[edit-prepend]]
  The string `page` is the title of the destination page.
  The string `content` is the content to replace the content of `page` with.
  The string `token` is the CSRF token needed to make the edit."
  [page content token]
  (def query-map {:action "edit"
                  :title page
                  :text content
                  :token token
                  :format "json"})
  (http/http-post query-map config))

;; FIXME: This function currently does no error handling to discern whether or
;;        not the edit failed (ss 6/10/20).
(defn edit-prepend
  "Prepends to the content of a page.
  This function in will prepend to the content of the page.
  For functions that just append to or replace the page see:
  [[edit-append]] and [[edit-replace]]
  The string `page` is the title of the destination page.
  The string `content` is the content to prepend to `page`.
  The string `token` is the CSRF token needed to make the edit."
  [page content token]
  (def query-map {:action "edit"
                  :title page
                  :prependtext content
                  :token token
                  :format "json"})
  (http/http-post query-map config))

;; FIXME: This function currently does no error handling to discern whether or
;;        not the edit failed (ss 6/10/20).
(defn edit-append
  "Appends to the content of a page.
  This function in will append to the content of the page.
  For functions that just append to or replace the page see:
  [[edit-prepend]] and [[edit-replace]]
  The string `page` is the title of the destination page.
  The string `content` is the content to append to `page`.
  The string `token` is the CSRF token needed to make the edit."
  [page content token]
  (def query-map {:action "edit"
                  :title page
                  :appendtext content
                  :token token
                  :format "json"})
  (http/http-post query-map config))
