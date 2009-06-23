;;;    Chart: A simple Clojure wrapping of JFreeChart factory methods
;;;    Copyright (C) 2009  Mark Fredrickson
;;;
;;;    This library is free software; you can redistribute it and/or
;;;    modify it under the terms of the GNU Lesser General Public
;;;    License as published by the Free Software Foundation; either
;;;    version 2.1 of the License, or (at your option) any later version.
;;;
;;;    This library is distributed in the hope that it will be useful,
;;;    but WITHOUT ANY WARRANTY; without even the implied warranty of
;;;    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
;;;    Lesser General Public License for more details.
;;;
;;;    You should have received a copy of the GNU Lesser General Public
;;;    License along with this library; if not, write to the Free Software
;;;    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

(ns com.dejcartes.chart
  (:refer-clojure)
  (:use clojure.contrib.seq-utils)
  (:import 
    (org.jfree.chart JFreeChart ChartFactory ChartFrame)
    (org.jfree.chart.plot PlotOrientation)
    (org.jfree.data.category CategoryDataset DefaultCategoryDataset)
    (org.jfree.data.general DefaultPieDataset PieDataset)
    (org.jfree.data.xy XYDataset XYSeries XYSeriesCollection)))


(def *defaults* {:legend true
		 :title ""
		 :cat-axis-label ""
		 :val-axis-label ""
		 :x-axis-label ""
		 :y-axis-label ""
		 :table-order []
		 :tooltips true
		 :urls false
		 :orientation PlotOrientation/VERTICAL})

(defn- params-to-map
  [& params]
  (if (map? (first params))
    (first params)
    (apply hash-map params)))

;;These maps wrap the Java factory methods
(def *cat-facts* {:area        (fn[t c v d o l tt u] (ChartFactory/createAreaChart t c v d o l tt u))
		  :bar         (fn[t c v d o l tt u] (ChartFactory/createBarChart t c v d o l tt u))
		  :bar3D       (fn[t c v d o l tt u] (ChartFactory/createBarChart3D t c v d o l tt u))
		  :line        (fn[t c v d o l tt u] (ChartFactory/createLineChart t c v d o l tt u))
		  :line3D      (fn[t c v d o l tt u] (ChartFactory/createLineChart3D t c v d o l tt u))
		  :waterfall   (fn[t c v d o l tt u] (ChartFactory/createWaterfallChart t c v d o l tt u))
		  })

(def *pie-facts* {:pie    (fn[t d l tt u] (ChartFactory/createPieChart t d l tt u))
		  :pie3D  (fn[t d l tt u] (ChartFactory/createPieChart3D t d l tt u))
		  :ring   (fn[t d l tt u] (ChartFactory/createRingChart t d l tt u))
		  })

(def *xy-facts* {:scatter      (fn[t x y d o l tt u] (ChartFactory/createScatterPlot t x y d o l tt u))
		 :xy-area      (fn[t x y d o l tt u] (ChartFactory/createXYAreaChart t x y d o l tt u))
		 :xy-line      (fn[t x y d o l tt u] (ChartFactory/createXYLineChart t x y d o l tt u))
		 :xy-step-area (fn[t x y d o l tt u] (ChartFactory/createXYStepAreaChart t x y d o l tt u))
		 :xy-step      (fn[t x y d o l tt u] (ChartFactory/createXYStepChart t x y d o l tt u))
		 })

(def *multi-pie-facts* {:multi-pie   (fn[t d to l tt u] (ChartFactory/createMultiplePieChart t d to l tt u))
			:multi-pie3D (fn[t d to l tt u] (ChartFactory/createMultiplePieChart t d to l tt u))
			})

;;Hopefully straightforward dispatch function.
(defn chart-dispatch
  [data map-params]
  (let [local-params (merge *defaults* map-params)
	chart-type (:type local-params)]
    (if chart-type
      (cond
       (*cat-facts* chart-type) ::category
       (*pie-facts* chart-type) ::pie
       (*xy-facts* chart-type) ::xy
       (*multi-pie-facts* chart-type) ::multi-pie
       ))))

(defn set-chart
  [#^JFreeChart chart & params]
  (let [local-params (apply params-to-map params)]
    (do
      (when (:anti-alias local-params)
	(. chart setAntiAlias (:anti-alias local-params)))
      (when (:background-img local-params)
	(. chart setBackgroundImage (:background-img local-params)))
      (when (:background-img-align local-params)
	(. chart setBackgroundImageAlignment (:background-img-align local-params)))
      (when (:background-img-alpha local-params)
	(. chart setBackgroundImageAlpha (:background-img-alpha local-params)))
      (when (:background-paint local-params)
	(. chart setBackgroundPaint (:background-paint local-params)))
      (when (:border-visible local-params)
	(. chart setBorderVisible (:border-visible local-params)))
      chart)))

(defmulti create-chart chart-dispatch)

(defmethod create-chart ::category
  [data map-params]
  (let [local-params (merge *defaults* map-params)
	factory (*cat-facts* (:type local-params))]
    (if factory
      (factory (:title local-params)
	       (:cat-axis-label local-params)
	       (:val-axis-label local-params)
	       data
	       (:orientation local-params)
	       (:legend local-params)
	       (:tooltips local-params)
	       (:urls local-params)))))

(defmethod create-chart ::pie
  [data map-params]
  (let [local-params (merge *defaults* map-params)
	factory (*pie-facts* (:type local-params))]
    (if factory
      (factory (:title local-params)
	       data
	       (:legend local-params)
	       (:tooltips local-params)
	       (:urls local-params)))))

(defmethod create-chart ::multi-pie
  [data map-params]
  (let [local-params (merge *defaults* map-params)
	factory (*pie-facts* (:type local-params))]
    (if factory
      (factory (:title local-params)
	       data
	       (:table-order local-params)
	       (:legend local-params)
	       (:tooltips local-params)
	       (:urls local-params)))))

(defmethod create-chart ::xy
  [data map-params]
  (let [local-params (merge *defaults* map-params)
	factory (*xy-facts* (:type local-params))]
    (if factory
      (factory (:title local-params)
	       (:x-axis-label local-params)
	       (:y-axis-label local-params)
	       data
	       (:orientation local-params)
	       (:legend local-params)
	       (:tooltips local-params)
	       (:urls local-params)))))
    

(defn build-chart
  "Not really supposed to be called directly."
  [data type param-map]
  (let [local-params (assoc param-map :type type)
	chart (create-chart data local-params)]
    chart))

    ;(if chart
    ;  (apply set-chart chart local-params))))

(defmacro defchart
  [name & body]
  (let [d-p (gensym "data_")
	p-p (gensym "params_")
	kw (keyword (str name))]
    `(defn ~name
       [~d-p & ~p-p]
       (let [~'local-chart (build-chart ~d-p ~kw (apply params-to-map ~p-p))]
	 (do
	   ~@body
	   ~'local-chart)))))

;Pie Charts
(defchart pie)
(defchart pie3D)
(defchart ring)

;;Category Charts
(defchart area)
(defchart bar)
(defchart bar3D)
(defchart line)
(defchart line3D)
(defchart waterfall)

;XY Charts
(defchart scatter)
(defchart xy-area)
(defchart xy-line)
(defchart xy-step-area)
(defchart xy-step)

;;Output Functions
(defn make-window
  [title chart]
  (doto (new ChartFrame title chart)
    (.pack)
    (.setVisible true)))

(defn to-png
  "NOt quite sure how to get JPEG output yet."
  [#^String filename #^JFreeChart chart]
  (javax.imageio.ImageIO/write 
   (. chart createBufferedImage 400 300)
   "PNG" 
   (java.io.File. filename)))
