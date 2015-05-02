
(ns owsy.eastwood-junit.core
  (:require [clojure.data.xml :refer [sexp-as-element]]
            [eastwood.lint :refer [lint]]))

(defn- to-testcase
  [{:keys [column linter line msg] :as warning}]
  (let [nss (:namespace-sym warning)]
    [:testcase {:classname nss
                :name (format "%s_line-%d_column-%d"
                              (name nss)
                              line
                              column)}
     [:failure {:type linter
                :message msg}]]))

(defn- xml-for
  [opts]
  (let [warnings (:warnings (lint opts))
        cases (map to-testcase warnings)]
    (apply
      conj
      [:testsuite {:tests (count warnings)}]
      (vec cases))))

;; Public
;; ------

(defn element-for
  [opts]
  (sexp-as-element
    (xml-for opts)))

