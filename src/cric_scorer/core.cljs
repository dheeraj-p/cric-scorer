(ns cric-scorer.core
  (:require [reagent.core :as r]
            [cric-scorer.home_page :refer [home]]))

(defn header []
  [:header "Cric Scorer"])

(defn footer []
  [:footer])

(defn main-content []
  [:main [home]])

(defn app-root []
  [:div [header] [main-content] [footer]])


(defn mount-root []
  (r/render [app-root] (.getElementById js/document "app")))

(defn init! []
  (mount-root))
