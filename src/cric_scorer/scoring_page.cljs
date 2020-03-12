(ns cric-scorer.scoring_page)


(defn toggle-class [ele toggled-class]
  (let [el-classList (.-classList ele)]
    (if (.contains el-classList toggled-class)
      (.remove el-classList toggled-class)
      (.add el-classList toggled-class))))


(defn toggle-ele [ele list]
  (if (some #(= ele %) list)
    (remove #(= ele %) list)
    (conj list ele)))

(defonce ball-types (atom []))

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
    [:tr (map (fn [val] [:th val]) headers)]]
   [:tbody (map
             (fn [row] [:tr
                        (map (fn [val] [:td val]) row)]) rows)]])

(defn current-players-display [batsmans bowler]
  [:div.score-comp.current-player-comp
   (display-table ["BATSPERSON" "R" "B" "4s" "6s" "SR"] (map vals batsmans))
   (display-table ["BOWLER" "O" "M" "R" "W" "EC"] (vector (vals bowler)))])

(defn ball-type-toggle [ball-type, a] (do
                             (toggle-class (.-target a) "selected-type")
                             (swap! ball-types #(toggle-ele ball-type %))))

(defn ball-type-div [type]
  [:div.ball-type {:on-click #(ball-type-toggle type %)} type])

(defn ball-type-display []
  (let [ball-types ["Wide" "No Ball"  "Byes" "Leg Byes" "Wicket"]]
    [:div.score-comp.ball-types (map ball-type-div ball-types)]))


(defn select-run-div [on-runs-input run]
  [:div.select-runs-option {:on-click #(on-runs-input (.-innerText (.-target %)))} run])

(defn select-runs-display [on-runs-input]
  (let [on-runs-input (partial on-runs-input @ball-types)]
    [:div.score-comp.select-runs
   (map (partial select-run-div on-runs-input) [0 1 2 3 4 5 6])
   [:input.select-runs-option.runs-input {:type :number :on-key-press
                                                #(when (= (.-key %) "Enter")
                                                          (on-runs-input (.-value (.-target %))))}]]))

(defn extra-options-display []
  [:div.score-comp.extra-options (map (fn [text] [:button.option-btn text]) ["Undo" "Swap Batsman" "Retire"])])

(defn scoring-page [match on-runs-input]
  [:div.scoring-page
   (score-header {:team1       {:name "Tilak's" :over 4.4 :runs 30 :wickets 2}
                  :team2       {:name "Dheeraj's" :over 0.0 :runs 0 :wickets 0}
                  :stat        {:CRR 2.2 :RRR 3}
                  :total-overs 12})
   (current-players-display [{:name "Tilak" :runs 7 :balls 2 :4s 0 :6s 1 :SR 350}
                             {:name "Phani" :runs 4 :balls 1 :4s 1 :6s 0 :SR 400}]
                            {:name "Dheeraj" :overs 0.4 :m 0 :runs 11 :wickets 1 :ec 15})
   (current-over-display [6, 1, "W", 4])
   (ball-type-display)
   (select-runs-display (partial println))
   (extra-options-display)])
