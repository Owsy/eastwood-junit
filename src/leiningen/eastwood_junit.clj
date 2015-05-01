
(ns leiningen.eastwood-junit
  (:require [clojure.data.xml :refer [emit-str]]
            [owsy.eastwood-junit.core :refer [element-for]]))

(defn eastwood-junit [project & [opts]]
  (println
    (emit-str
      (element-for
        (read-string opts)))))

