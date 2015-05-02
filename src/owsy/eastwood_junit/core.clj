
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
        suite [:testsuite {:tests (count warnings)}]
        cases (map to-testcase warnings)]
    (if (not (empty cases))
      (apply conj suite (vec cases))
      suite)))

;; Public
;; ------

(defn element-for
  [opts]
  (sexp-as-element
    (xml-for opts)))

