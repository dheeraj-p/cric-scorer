(ns cric-scorer.core
  (:require [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [cric-scorer.home_page :refer [home]]
            [cric-scorer.select_initial_players_page :refer [select-initial-players]]
            [cric-scorer.api_client :refer [fetch-action]]
            [cric-scorer.scoring_page :refer [scoring-page]]))

(defonce match (r/atom nil))

(defn header []
  [:header "Cric Scorer"])

(defn footer []
  [:footer])

(defn main-content []
  [:main (if @match
           (let [view (:view (:data @match))]
             [view @match]))])

(defn perform-action [action]
  (case action
    :ACTION_SELECT_INITIAL_PLAYERS (rfe/push-state ::select-initial-players)
    :ACTION_CREATE_GAME (rfe/push-state ::home)
    :ACTION_PLAY (rfe/push-state ::scoring-page)))

(defn app-root []
  [:div [header] [main-content] [footer]])

(def routes
  [["/"
    {:name ::home
     :view home}]
   ["/select_initial_players"
    {:name ::select-initial-players
     :view select-initial-players}]
   ["/scoring-page"
    {:name ::scoring-page
     :view scoring-page}]])

(defn mount-root []
  (fetch-action perform-action #())
  (r/render [app-root] (.getElementById js/document "app")))

(defn init! []
  (rfe/start!
    (rf/router routes)
    (fn [m] (reset! match m))
    {:use-fragment true})
  (mount-root))

(comment "This is a  comment")