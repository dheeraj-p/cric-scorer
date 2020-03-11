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
  [:div.score-comp.score-header
   (score-display (:team1 score-infos) (:total-overs score-infos))
   (score-display (:team2 score-infos) (:total-overs score-infos))
   (score-stat (:stat score-infos))])

(defn current-over-display [current-over]
  [:div.score-comp.this-over-comp
   [:span "This over :"]
   (map (fn [run]
          [:span.run {:class (str "run-" run)} run]) current-over)])

(defn display-table [headers rows]
  [:table
   [:thead
    (map (fn [val] [:th val]) headers)]
   [:tbody (map
             (fn [row] [:tr
                        (map (fn [val] [:td val]) row)]) rows)]])

(defn current-players-display [batsmans bowler]
  [:div.score-comp.current-player-comp
   (display-table ["BATSPERSON" "R" "B" "4s" "6s" "SR"] (map vals batsmans))
   (display-table ["BOWLER" "O" "M" "R" "W" "EC"] (vector (vals bowler)))])

(defn scoring-page [match]
  [:div.scoring-page
   (score-header {:team1       {:name "Tilak's" :over 4.4 :runs 10 :wickets 2}
                  :team2       {:name "Dheeraj's" :over 0.0 :runs 0 :wickets 0}
                  :stat        {:CRR 2.2 :RRR 3}
                  :total-overs 12})
   (current-players-display [{:name "Tilak" :runs 6 :balls 2 :4s 0 :6s 1 :SR 300}
                             {:name "Phani" :runs 6 :balls 4 :4s 1 :6s 0 :SR 150}]
                            {:name "Dheeraj" :overs 1 :m 0 :runs 10 :wickets 0 :ec 10})
   (current-over-display [1, 2, 4, "W"])])