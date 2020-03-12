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

(defn http-get [url _params on-success on-failure]
    (-> url
        (js/fetch)
        (.then #(.json %))
        (.then js->clj)
        (.then on-success)
        (.catch (comp on-failure js->clj))))

(defn fetch-action [on-success on-failure]
    (http-get "http://localhost:8000/match-action" {} #(-> (keyword (get % "action")) on-success) on-failure))

(defn register-match [game-data on-success on-failure]
    (http-post "http://localhost:8000/register-match" game-data on-success on-failure))

(defn register-initial-players [player-names on-success on-failure]
    (http-post "http://localhost:8000/register-initial-players" player-names on-success on-failure))
