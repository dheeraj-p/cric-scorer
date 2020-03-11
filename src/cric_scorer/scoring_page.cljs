(ns cric-scorer.scoring_page)

(defn score-display [score-info total-overs]
  [:div.score
   [:div [:span (:name score-info)]]
   [:div.score-info [:span.runs-info [:span.runs (str (:runs score-info) " / " (:wickets score-info))]
          [:span (str "( " (:over score-info) " / " total-overs " )")]]]])

(defn score-stat [stat]
  [:div.score-stat
   [:span.stat (str "CRR : " (:CRR stat))]
   [:span.stat (str "RRR : " (:RRR stat))]])

(defn score-header [score-infos]
  [:div.score-comp
   (score-display (:team1 score-infos) (:total-overs score-infos))
   (score-display (:team2 score-infos) (:total-overs score-infos))
   (score-stat (:stat score-infos))])

(defn scoring-page [match]
  [:div.scoring-page (score-header {:team1       {:name "Tilak's" :over 4.4 :runs 10 :wickets 2}
                                    :team2       {:name "Dheeraj's" :over 0.0 :runs 0 :wickets 0}
                                    :stat        {:CRR 2.2 :RRR 3}
                                    :total-overs 12})])