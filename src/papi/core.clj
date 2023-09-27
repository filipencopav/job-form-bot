(ns papi.core
  (:gen-class)
  (:require [papi.setup :as setup]
            [taoensso.timbre :as log]))

(defonce bot-process (atom nil))

(defn stop []
  (and
   (some-> @bot-process
    future?)
    (future-cancel @bot-process)))

(defn start []
  (stop)
  (reset! bot-process (future (setup/event-loop (setup/create-bot)))))

(defn -main
  "I don't do shit... yeet."
  [& args]
  (start)
  (deref bot-process))
