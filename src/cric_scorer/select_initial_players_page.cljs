(ns cric-scorer.select_initial_players_page)

(defn select-initial-players [] [:div.flex-container-small
                                 [:input.size-large {:type "text" :placeholder "Striker"}]
                                 [:input.size-large {:type "text" :placeholder "Non-Striker"}]
                                 [:input.size-large {:type "text" :placeholder "Bowler" :min-length 1}]
                                 [:button.size-large "Next"]])