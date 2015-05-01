
(ns owsy.eastwood-junit.core
  (:require [clojure.data.xml :refer [emit-str sexp-as-element]]
            [eastwood.lint :refer [lint]]))

(defn by-ns
  [acc warning]
  (let [ns-key (-> warning :namespace-sym keyword)
        info {:type (:linter warning)
              :message (:msg warning)}]
    (if (ns-key acc)
      (update-in acc [ns-key] conj info)
      (merge acc {ns-key [info]}))))

(defn to-failure
  [warning]
  [:failure warning])

(defn to-testcase
  [ns-key warnings]
  (apply
    conj
    [:testcase {:classname ns-key
                :name ns-key}]
    (vec (map to-failure warnings))))

;; Public
;; ------

(defn element-for
  [opts]
  (let [data (lint opts)
        res (reduce by-ns {} (:warnings data))
        cases (vec (map to-testcase (keys res) (vals res)))
        xml (apply conj [:testsuite {:tests (count (keys res))}] cases)]
    (sexp-as-element xml)))

