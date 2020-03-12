(ns cric-scorer.select_initial_players_page
  (:require [reagent.core :as r]
            [reitit.frontend.easy :as rfe]
            [cric-scorer.api_client :refer [register-initial-players]]
            [cric-scorer.utils :refer [alert]]))

(def initial-players-data (r/atom {}))

(defn update-player-name [property event]
  (r/rswap! initial-players-data assoc property (.-target.value event)))

(def update-striker-name (partial update-player-name :striker))

(def update-non-striker-name (partial update-player-name :non-striker))

(def update-bowler-name (partial update-player-name :bowler))

(defn redirect-to-scoring-page [_]
  (rfe/push-state :scoring-page))

(defn on-click-next []
  (register-initial-players initial-players-data redirect-to-scoring-page alert))

(defn select-initial-players [] [:div.flex-container-small
                                 [:input.size-large {:type "text" :placeholder "Striker" :on-change update-striker-name}]
                                 [:input.size-large {:type "text" :placeholder "Non-Striker" :on-change update-non-striker-name}]
                                 [:input.size-large {:type "text" :placeholder "Bowler" :on-change update-bowler-name}]
                                 [:button.size-large {:on-click on-click-next} "Next"]])