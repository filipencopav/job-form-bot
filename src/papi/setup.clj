(ns papi.setup
  (:gen-class)
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.edn :as edn]
            [telegrambot-lib.core :as tbot]
            [taoensso.timbre :as log]
            [clojure.spec.alpha :as s]
            [papi.handling :as handling]))

(def config (edn/read-string (slurp "config.edn")))
(def sleepy-time
  {:sleep 500        ; Thread/sleep is in millis
   :conn-timeout 5}) ; timeout in seconds

(defonce update-id (atom nil))
(defn set-id!
  "Sets the update id to process next as the the passed in `id`."
  [id]
  (reset! update-id id))

(defn poll-updates
  "Long poll for recent chat messages from Telegram."
  ([bot] (poll-updates bot nil))
  ([bot offset]
   (try
     (tbot/get-updates bot {:timeout (str (:conn-timeout sleepy-time)), :offset (str offset)})
     (catch Exception e
       (log/error "tbot/get-updates exception:" e)))))

(defn event-loop [bot]
  (loop []
    (let [resp (poll-updates bot @update-id)
          updates (:result resp)]
      (doseq [upd updates]
        (handling/handle-update bot upd)
        (-> upd
         :update_id
         inc
         set-id!))
      (Thread/sleep (:sleep sleepy-time)))
    (recur)))

(defn create-bot []
  (-> (slurp "config.edn")
      edn/read-string
      :bot-token
      tbot/create))
