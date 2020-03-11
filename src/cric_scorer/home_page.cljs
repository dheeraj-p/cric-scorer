(ns cric-scorer.home_page
  (:require [reagent.core :as r]
            [reagent.cookies :as cookies]
            [reitit.frontend.easy :as rfe]
            [cric-scorer.api_client :refer [http-post]]
            [cric-scorer.utils :refer [redirect-to alert]]))

(defonce game-data (r/atom {:first-team "First Team" :second-team "Second Team" :overs 0}))

(defn on-game-start [_]
  (rfe/push-state :select-initial-players))

(defn start-match []
  (http-post "http://localhost:8000/start-match" @game-data on-game-start alert))

(defn on-change-team-name [property event]
  (r/rswap! game-data assoc property (.-target.value event)))

(def change-first-team-name (partial on-change-team-name :first-team))

(def change-second-team-name (partial on-change-team-name :second-team))

(def change-number-of-overs (partial on-change-team-name :overs))

(defn home []
  [:div.flex-container-small
   [:input.size-large {:type "text" :placeholder "First Team" :on-change change-first-team-name}]
   [:input.size-large {:type "text" :placeholder "Second Team" :on-change change-second-team-name}]
   [:input.size-large {:type "number" :placeholder "Overs" :min-length 1 :on-change change-number-of-overs}]
   [:button.size-large {:on-click start-match} "Start Match"]])