
(ns leiningen.eastwood-junit
  (:require [clojure.data.xml :refer [emit-str]]))

(def main
  (delay
    (do
      (require 'owsy.eastwood-junit.core)
      (resolve (symbol "owsy.eastwood-junit.core/run-from-cmdline")))))

(defn eastwood-junit [project & [opts]]
  (let [opts (read-string (or opts "{}"))]
    (@main opts)))

