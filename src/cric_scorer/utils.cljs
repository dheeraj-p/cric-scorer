(ns cric-scorer.utils)

(defn json-stringify [json-object]
  (JSON/stringify (clj->js json-object)))

(defn alert [msg]
  (js/alert msg))

(defn redirect-to [url]
  (set! js/window.location.href url))