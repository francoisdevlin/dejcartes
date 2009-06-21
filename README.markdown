This is my fork of the Dejcartes Library, written by Mark Fredrickson.  His original work is available here:

http://www.markmfredrickson.com/code/

Dejcartes is a Clojure interface to the JFreeChart charting and graphing 
library. A simple interface to be sure, but practical for graphing many data 
sources. Dejcartes is mostly a wrapper around the ChartFactory static methods. 
This interface suits Clojure's functional paradigm very well.

Chart supports using native Clojure collections for data sources. This makes 
graphing from Clojure applications very intuitive. Lists, vectors, and 
hash-maps are translated into JFreeChart's native data types behind the scenes 
as needed. A demo showing many the supported functions and Clojure native data 
structures is included and can be run from the command line with "ant demo". Here 
is a brief excerpt:

    (require '[com.dejcartes.chart :as chart])
        
    ; first, some categorical data
    (def editor-survey
      {2009
        {"emacs" 312 "vim" 234 "eclipse" 193 "netbeans" 82 "other" 26}
       2008
        {"vim" 192 "emacs" 267 "eclipse" 297 "other" 75}})
    
    ;; a pie chart of the 2009 data
	(chart/make-window "Editor Survey" 
		(chart/pie (editor-survey 2009)))

And you should see a chart that looks like this:

<img src="docs/imgs/test-pie.png">

(docs/imgs/test-pie.png)

Dejcartes is a young project and is lacking in comprehensive documentation. The 
best reference right now is the demo.clj example code and the JFreeChart API 
documentation. Future improvements will include more documentation, an Agent 
based interface for interacting with chart windows, and a persistent (read: 
non-mutating) interface for annotations and other more powerful chart features.

Dejcartes is licensed under the GNU Lesser GPL, which is consistent with 
JFreeChart's license.

To build Dejcartes:
    $ ant build

See demo.clj for including Dejcartes in your projects.
