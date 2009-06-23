;; This is a collection of demo data for Dejcartes.  I hope you enjoy playing with it!
;; - Sean

(ns com.dejcartes.demo-data
  (:require [com.dejcartes [data-series :as series]]))

(def editor-survey
     {2009
      {"emacs" 312 "vim" 234 "eclipse" 193 "netbeans" 82 "other" 26}
      2008
      {"vim" 192 "emacs" 267 "eclipse" 297 "other" 75}})
