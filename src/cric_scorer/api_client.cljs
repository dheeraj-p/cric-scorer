(ns cric-scorer.api_client
    (:require [cric-scorer.utils :refer [json-stringify]]))

(defn http-post [url body on-success on-failure]
  (-> url
      (js/fetch (clj->js {:method  "POST"
                          :headers {"Content-Type" "application/json"}
                          :body    (json-stringify body)}))
      (.then #(.json %))
      (.then js->clj)
      (.then on-success)
      (.catch (comp on-failure js->clj))))