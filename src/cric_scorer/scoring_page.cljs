(ns cric-scorer.scoring_page
  (:require [reagent.core :as r]
            [cric-scorer.api_client :refer [fetch-match-data update-match-data]]))

(defn debug [x] (println x) x)

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
  [:div.ball-type {:id type :on-click #(ball-type-toggle type %)} type])

(defn ball-type-display []
  (let [ball-types ["Wide" "No Ball" "Byes" "Leg Byes" "Wicket"]]
    [:div.score-comp.ball-types (map ball-type-div ball-types)]))


(defn select-run-div [on-runs-input run]
  [:div.select-runs-option {:on-click #(on-runs-input (.-innerText (.-target %)))} run])

(defn select-runs-display [on-runs-input]
  (let [on-runs-input #(on-runs-input @ball-types %)]
    [:div.score-comp.select-runs
     (map (partial select-run-div on-runs-input) [0 1 2 3 4 5 6])
     [:input.select-runs-option.runs-input {:type :number :on-key-press
                                                  #(when (= (.-key %) "Enter")
                                                     (on-runs-input (.-value (.-target %))))}]]))

(defn extra-options-display [options-funcs]
  [:div.score-comp.extra-options
   (map (fn [option] [:button.option-btn {:on-click (fn [x] (second option))} (first option)])
        {"Undo"         (first options-funcs)
         "Swap Batsman" (second options-funcs)
         "Retire"       (last options-funcs)})])

(defonce match-state (r/atom {}))

(defn reset-ball-type []
  (doseq [ball-type @ball-types]
    (.remove (.-classList (.getElementById js/document ball-type)) "selected-type"))
  (reset! ball-types []))

(defn update-match [ball-types runs]
  (update-match-data [ball-types runs] #(r/rswap! match-state (constantly %)) identity)
  (reset-ball-type))

(defn scoring-page-component [options-funcs]
  [:div.scoring-page
   (score-header (:score-header @match-state))
   (current-players-display (:current-batsmen-stats @match-state)
                            (:current-bowler-stats @match-state))
   (current-over-display (:current-over @match-state))
   (ball-type-display)
   (select-runs-display update-match)
   (extra-options-display [identity identity identity])])

(def scoring-page (with-meta scoring-page-component
                             {:component-did-mount (fn [] (fetch-match-data #(r/rswap! match-state (constantly %)) identity))}))
