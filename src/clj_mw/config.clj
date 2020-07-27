(ns clj-mw.config)

;;; configuration
; this is the configuration struct and everything that you need to configure
; application can be found and modified here.
;
;; username [string]
;; password [string]
;; host     [string]
;; api-path [string]
;; ssl      [boolean]
(def configuration (create-struct
                     :username
                     :password
                     :protocol
                     :host
                     :api-path))

;;; config-example
; this is an example of a proper configuration.
; please fill in your information here in order to login to your bot account.
;
; NOTE: do not commit your actual config to git and do not share it with other
; people. I do not provide any means of loading configurations from disk nor do
; I make any effort to load them from an environment variable. You are free to
; do this yourself just as long as the result in a valid config structure.
;
; UCP NOTICE: in order to use this on a UCP wiki you must visit
; Special:BotPasswords and generate credentials there BEFORE they can be used
; here to login.
(def config
  (struct configuration (System/getenv "USER")
          (System/getenv "PASS")
          "https://"
          (System/getenv "URL")
          "/api.php"))
