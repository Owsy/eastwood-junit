
(ns leiningen.eastwood-junit
  (:require [leiningen.core.eval :refer [eval-in-project]]))

(defn eastwood-junit
  ([project] (eastwood-junit project "{}"))
  ([project opts]
   (eval-in-project
     (update-in project [:dependencies] conj ['owsy/eastwood-junit "0.3.1"])
     `(owsy.eastwood-junit.core/run-from-cmdline
        (read-string '~opts))
     '(require 'owsy.eastwood-junit.core))))

