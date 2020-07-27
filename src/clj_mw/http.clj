(ns clj-mw.http
  (:require [clj-http.client :as client]
            [clj-http.cookies :as cookies]
            [clojure.data.json :as json]))

(def cookie-store (cookies/cookie-store))

(defn http-async-get
  "Performs an async HTTP GET.
  This function serves as a network interface. It takes a hashmap for the
  querystring `query` and a struct for the uri fragments `config`. It also
  takes two lambdas for callbacks. For HTTP POST see: [[http-async-post]].
  The hashmap `query` contains keys and values to build a querystring from.
  The struct `config` contains at least `protocol`, `host`, and `api-path`.
  The lambda `success` is for an OK HTTP request.
  The lambda `failure` is for an HTTP request that failed."
  [query config success failure]
  (client/get (str (:protocol config)
                    (:host config)
                    (:api-path config))
               {:accept :json
                :async? true
                :cookie-policy :standard
                :cookie-store cookie-store
                :query-params query}
               success
               failure))

(defn http-async-post
  "Performs an async HTTP POST.
  This function serves as a network interface. It takes a hashmap for the
  form body `query` and a struct for the uri fragments `config`. It also
  takes two lambdas for callbacks. For HTTP GET see: [[http-async-get]].
  The hashmap `query` contains keys and values to build a form body from.
  The struct `config` contains at least `protocol`, `host`, and `api-path`.
  The lambda `success` is for an OK HTTP request.
  The lambda `failure` is for an HTTP request that failed."
  [query config success failure]
  (client/post (str (:protocol config)
                    (:host config)
                    (:api-path config))
               {:accept :json
                :async? true
                :cookie-policy :standard
                :cookie-store cookie-store
                :form-params query}
               success
               failure))

(defn http-get
  "Performs an HTTP GET.
  This function serves as a network interface. It takes a hashmap for the
  querystring `query` and a struct for the uri fragments `config`. For
  HTTP POST see: [[http-post]].
  The hashmap `query` contains keys and values to build a querystring from.
  The struct `config` contains at least `protocol`, `host`, and `api-path`."
  [query config]
  (json/read-str (:body
                   (client/get (str (:protocol config)
                                    (:host config)
                                    (:api-path config))
                               {:accept :json
                                :cookie-policy :standard
                                :cookie-store cookie-store
                                :query-params query}))))

(defn http-post
  "Performs an HTTP POST.
  This function serves as a network interface. It takes a hashmap for the
  body of the form `query` and a struct for the uri fragments `config`. For
  HTTP GET see: [[http-get]].
  The hashmap `query` contains keys and values to build a form body from.
  The struct `config` contains at least `protocol`, `host`, and `api-path`."
  [query config]
  (json/read-str (:body
                   (client/post (str (:protocol config)
                                     (:host config)
                                     (:api-path config))
                                {:accept :json
                                 :cookie-policy :standard
                                 :cookie-store cookie-store
                                 :form-params query}))))
