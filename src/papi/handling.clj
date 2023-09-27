(ns papi.handling
  (:gen-class)
  (:require [taoensso.timbre :as log]))

(defn handle-message [message]
  (log/debug (:text message)))

(defn handle-update [bot update]
  (cond
    (:message update) (handle-message (:message update))
    (:callback_query_id update) "Bot button pressed"))

(comment
  (log/debug (get-in update [:message :text]))
  )
