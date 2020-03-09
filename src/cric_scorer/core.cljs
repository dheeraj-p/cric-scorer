(ns cric-scorer.core
  (:require [reagent.core :as r]
              [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [cric-scorer.home_page :refer [home]]
            [cric-scorer.game_page :refer [game]]))

(defonce match (r/atom nil))

(defn header []
  [:header "Cric Scorer"])

(defn footer []
  [:footer])

(defn main-content []
  [:main (if @match
           (let [view (:view (:data @match))]
             [view @match]))])

(defn app-root []
  [:div [header] [main-content] [footer]])

(def routes
  [["/"
    {:name :home
     :view home}]
   ["/game"
    {:name :game
     :view game}]])

(defn mount-root []
  (r/render [app-root] (.getElementById js/document "app")))

(defn init! []
  (rfe/start!
    (rf/router routes)
    (fn [m] (reset! match m))
    {:use-fragment true})
  (mount-root))
