(ns test-watcher.core
  (:require
   [clojure.test :as test]
   [clojure.tools.namespace.repl :as ctn.repl]
   [clojure.tools.namespace.reload :as ctn.reload]
   [clojure.tools.namespace.dir :as ctn.dir]
   [clojure.tools.namespace.track :as ctn.track]
   [cognitect.test-runner :as test-runner]))

(defn -main
  [& args]
  (println "Starting test-watcher")
  (loop [tracker (ctn.track/tracker)]
    (let [new-tracker (ctn.dir/scan-dirs tracker ["test"])
          changed     (not= tracker new-tracker)]
      (when changed
        (ctn.reload/track-reload new-tracker)
        (test-runner/test {}))
      (Thread/sleep 200)
      (recur new-tracker))))
