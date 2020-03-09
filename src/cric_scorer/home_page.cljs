(ns cric-scorer.home_page
  (:require [reagent.core :as r]
            [cric-scorer.api_client :refer [http-post]]))

(defonce team-names (r/atom {:first-team "First Team" :second-team "Second Team"}))

(defn alert [msg]
  (js/alert msg))

(defn start-match []
  (http-post "http://localhost:8000/start-match" @team-names alert alert))

(defn on-change-team-name [team event]
  (r/rswap! team-names assoc team (.-target.value event)))

(defn home []
  [:div [:input {:type "text" :placeholder "First Team" :on-change (partial on-change-team-name :first-team)}]
   [:input {:type "text" :placeholder "Second Team" :on-change (partial on-change-team-name :second-team)}]
   [:button {:on-click start-match} "Start Match"]])