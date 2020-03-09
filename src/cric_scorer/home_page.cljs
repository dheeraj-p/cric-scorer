(ns cric-scorer.home_page
  (:require [reagent.core :as r]
            [reagent.cookies :as cookies]
            [reitit.frontend.easy :as rfe]
            [cric-scorer.api_client :refer [http-post]]
            [cric-scorer.utils :refer [redirect-to alert]]))

(defonce team-names (r/atom {:first-team "First Team" :second-team "Second Team"}))

(defn on-game-start [_]
  (cookies/set! :game-status 'in-play)
  (rfe/replace-state :game))

(defn start-match []
  (http-post "http://localhost:8000/start-match" @team-names on-game-start alert))

(defn on-change-team-name [team event]
  (r/rswap! team-names assoc team (.-target.value event)))

(defn home []
  [:div {:class "home-container"}
   [:input.size-large {:type "text" :placeholder "First Team" :on-change (partial on-change-team-name :first-team)}]
   [:input.size-large {:type "text" :placeholder "Second Team" :on-change (partial on-change-team-name :second-team)}]
   [:input.size-large {:type "number" :placeholder "Overs" :min-length 1 :on-change (partial on-change-team-name :second-team)}]
   [:button.size-large {:on-click start-match} "Start Match"]])