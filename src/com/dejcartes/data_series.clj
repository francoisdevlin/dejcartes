(ns com.dejcartes.data-series
  (:refer-clojure)
  (:use clojure.contrib.seq-utils)
  (:import 
    (org.jfree.chart JFreeChart ChartFactory ChartFrame)
    (org.jfree.chart.plot PlotOrientation)
    (org.jfree.data.category CategoryDataset DefaultCategoryDataset)
    (org.jfree.data.general DefaultPieDataset PieDataset)
    (org.jfree.data.xy XYDataset XYSeries XYSeriesCollection)))

(defn math-map
  "This function is used to plot math functions over a specified range.  The name sucks something fierce."
  [f data] 
  (vec (map #(vector % (f %)) data)))

(defn cat-data
  "Internal data to create category datasets.  Expects a map as an input... sorta"
  ([seqs]
    (let [cat (new DefaultCategoryDataset)]
      (doseq [[title data] seqs]
        (if (coll? data)
          (doseq [[idx v] (indexed data)]
            (if (coll? v)
              (.addValue cat (second v) (first v) title)
              (.addValue cat v idx title)))
          (.addValue cat data 0 title)))
      cat)))

(defn pie-data
  "Internal function to convert from a sequence of pairs into a pie chart dataset"
  ([data]
    (let [pds (new DefaultPieDataset)]
      (doseq [i data] (.setValue pds (first i) (second i)))
      pds)))

(defn xy-series
  "Internal function to make an XYSeries out of a seq"
  ([sequence] (xy-series "" sequence))
  ([title sequence]
    (let [xys (new XYSeries title)]
      (doseq [pair sequence] (.add xys (first pair) (second pair)))
      xys)))

(defn xy-collection
  "Internal function to convert a set of seqs into xy pair sequences
   Each entry is expected to be a seq, with a format like
   [\"Title\" xy-series]"
  ([seqs]
   (let [ds (new XYSeriesCollection)]
     (doseq [[title series] seqs]
       (.addSeries ds (xy-series title series)))
     ds)))