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

;; This is a collection of demo data for Dejcartes.  I hope you enjoy playing with it!
;; - Sean

(ns com.dejcartes.demo-data
  (:require [com.dejcartes [data-series :as series]]))

(def editor-survey
     {2009
      {"emacs" 312 "vim" 234 "eclipse" 193 "netbeans" 82 "other" 26}
      2008
      {"vim" 192 "emacs" 267 "eclipse" 297 "other" 75}})

(def degrees (range 0 360))

(defn to-rad
  "Converts degrees to radians"
  [x]
  (java.lang.Math/toRadians x))

(defn sin
  "Computes sine in radians"
  [x]
  (java.lang.Math/sin x))

(defn cos
  "Computes cosine in radians"
  [x]
  (java.lang.Math/cos x))

(defn sin-d
  [deg]
  (sin (to-rad deg)))

(defn cos-d
  [deg]
  (cos (to-rad deg)))

