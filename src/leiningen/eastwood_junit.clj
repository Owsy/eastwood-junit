
(ns leiningen.eastwood-junit
  (:require [leiningen.core.eval :refer [eval-in-project]]))

(defn eastwood-junit
  [project & [opts]]
  (eval-in-project
    (update-in project [:dependencies] conj ['owsy/eastwood-junit "0.3.0"])
    `(owsy.eastwood-junit.core/run-from-cmdline '~opts)
    '(require 'owsy.eastwood-junit.core)))

