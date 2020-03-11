(ns cric-scorer.scoring_page)

(defn score-display [team-name score]
  [:div.value-display
   [:span.value-header.team-name team-name]
   [:span [:span.runs (str (:runs score) " - " (:wickets score))]
    [:span (str "( " (:over score) " )")]]])

(defn run-rate-display [run-rate]
  [:div.value-display
   [:span.value-header.low-focus "CRR"]
   [:span.run-rate run-rate]])

(defn score-header [team-name score run-rate]
  [:div.score-comp [:div.score (score-display team-name score) (run-rate-display run-rate)]])

(defn scoring-page [match]
  [:div.scoring-page (score-header "Tilak's" {:over 4.4 :runs 10 :wickets 2} 2.2)])