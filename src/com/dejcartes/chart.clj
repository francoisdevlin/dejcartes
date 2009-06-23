;;;    Chart: A simple Clojure wrapping of JFreeChart factory methods
;;;    Copyright (C) 2009  Mark Fredrickson
;;;
;;;    Additions by Sean Devlin
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
  (:use clojure.contrib.seq-utils
	clojure.contrib.str-utils)
  (:import 
    (org.jfree.chart JFreeChart ChartFactory ChartFrame)
    (org.jfree.chart.plot PlotOrientation)
    (org.jfree.data.category CategoryDataset DefaultCategoryDataset)
    (org.jfree.data.general DefaultPieDataset PieDataset)
    (org.jfree.data.xy XYDataset XYSeries XYSeriesCollection)))

(defn capitalize
  [#^String input-str]
  (str (.toUpperCase (str (first input-str))) 
       (apply str (rest input-str))))

(defn camelize
  [#^String input-str]
  (let [words (seq (.split input-str "[-\\s+]"))]
    (apply str (.toLowerCase (first words)) (map capitalize (rest words)))))

(defmacro set-bean
  [a-bean a-key a-map]
  (let [bean-accessor-sym (symbol (str "." (camelize (apply str "set " (rest (str a-key))))))]
    `(when (contains? ~a-map ~a-key)
       (~bean-accessor-sym ~a-bean (~a-map ~a-key)))))

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
      (set-bean chart :anti-alias local-params)
      (set-bean chart :background-image local-params)
      (set-bean chart :background-image-alignment local-params)
      (set-bean chart :background-image-alpha local-params)
      (set-bean chart :background-paint local-params)
      (set-bean chart :border-stroke local-params)
      (set-bean chart :border-paint local-params)
      (set-bean chart :border-visible local-params)
      (set-bean chart :notify local-params)
      (set-bean chart :padding local-params)
      (set-bean chart :rendering-hints local-params)
      (set-bean chart :subtitles local-params)
      (set-bean chart :text-anti-alias local-params)
      (set-bean chart :title local-params)
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
    (if chart
      (set-chart chart local-params))))

(defmacro defchart
  ([name] (defchart name (gensym "chart-name_") (gensym "params-map-name_")))
  ([name chart-name param-map-name & body]
     (let [d-p (gensym "data_")
	   p-p (gensym "params_")
	   kw (keyword (str name))]
       `(defn ~name
	  [~d-p & ~p-p]
	  (let [~param-map-name (apply params-to-map ~p-p)
		~chart-name (build-chart ~d-p ~kw ~param-map-name)]
	    (do
	      ~@body
	      ~chart-name))))))

(defmacro render-context
  [map-binding object-binding & body]
  `(let [~(first map-binding) (~(second map-binding) :renderer)]
     (when ~(first map-binding)
       (let [~(first object-binding) (.. ~(second object-binding) getPlot getRenderer)]
	 (do ~@body)))))

(defmacro plot-context
  [map-binding object-binding & body]
  `(let [~(first map-binding) (~(second map-binding) :plot)]
     (when ~(first map-binding)
       (let [~(first object-binding) (. ~(second object-binding) getPlot)]
	 (do ~@body)))))	     

;Pie Charts
(defchart pie local-chart local-map
  (plot-context [plot-opts local-map]
		[plot local-chart]
		(set-bean plot :ignore-zero-values plot-opts)
		(set-bean plot :ignore-null-values plot-opts)))

(defchart pie3D)
(defchart ring)

;;Category Charts
(defchart area)

(defchart bar local-chart local-map
  (do
    (render-context [render-opts local-map]
		    [renderer local-chart]
		    (set-bean renderer :draw-bar-outline render-opts)
		    (set-bean renderer :item-margin render-opts))))
	  
(defchart bar3D local-chart local-map
  (do
    (render-context [render-opts local-map]
		    [renderer local-chart]
		    (set-bean renderer :draw-bar-outline render-opts)
		    (set-bean renderer :item-margin render-opts))))

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
  "Not quite sure how to get JPEG output yet."
  [#^String filename #^JFreeChart chart]
  (javax.imageio.ImageIO/write 
   (. chart createBufferedImage 400 300)
   "PNG" 
   (java.io.File. filename)))
