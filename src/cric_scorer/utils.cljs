(ns cric-scorer.utils)

(defn json-stringify [json-object]
  (JSON/stringify (clj->js json-object)))