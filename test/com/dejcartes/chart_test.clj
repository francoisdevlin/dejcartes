; (use clojure.contrib.test-is)


;;; XY Based Data Plots ;;;


;  {:test (fn []
;   (is (instance? XYSeries (xy-series :title '((1 2) (3 4) (4 5))))))})

;  {:test (fn []
;    (is
;      (instance?
;        XYDataset
;        (xy-series-collection {"Series 1" '((0 2) (1 2) (3 4)) "Series 2" '((1 10) (2 8) (4 7))}))))})

(deftest xy-dataset-test
  (let [xy-data {"Series 1" '((1 2) (3 4) (4 2)) "Series 2" '((3 4) (2 3) (3 4))}]
    (are (instance? JFreeChart _1)
      (scatter "scatter chart" "x-axis" "y-axis" xy-data)
      (polar "polar chart" xy-data)
      (xy-bar "title" "x axis" false "y axis" xy-data)
      (histogram "title" "x axis" "y axis" xy-data))))

;;; Agents for viewing charts
(with-test
  (defn view [title chart]
    (let [a (agent false)]
      (send a (fn [_] (doto (new ChartFrame title chart)
                        (.pack)
                        (.setVisible true)))))))

(deftest test-cat-data-plots
  (let [cat {"Emacs" 20 "Vi" 30 "Eclipse" 5}]
    (are (instance? JFreeChart _1)
      (bar "Bar Chart" "cat axis" "value axis" cat)
      (area "Area chart" "cat axis" "value axis" cat)
      (stacked-area "Area chart" "cat axis" "value axis" cat)
      (line "line chart" "cat axis" "value axis" cat))))

;  {:test (fn []
;    (is (instance? CategoryDataset (cat-data {"foo" '(1 2 3 4 5)})))
;    (is (instance? CategoryDataset (cat-data
;         {"foo" {"a" 1 "b" 2}}))))})

;  {:test (fn []
;    (is (instance? PieDataset (pie-dataset {"Hello" 123 "World" 456}))))})

;  {:test (fn []
;    (let [test-chart (pie "Test Title" '(("Emacs" 20) ("Vi" 15) ("Eclipse" 30)))] 
;      (is (instance? JFreeChart test-chart))))})
