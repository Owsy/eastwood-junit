
(ns owsy.eastwood-junit.core
  (:require [clojure.data.xml :refer [emit sexp-as-element]]
            [eastwood.lint :as eastwood]))

(defn- conj-if [col v]
  (if (empty? v)
    col
    (apply conj col (vec v))))

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

(defn- to-testsuite
  [nss warnings]
  (conj-if
    [:testsuite {:package nss}]
    (map to-testcase warnings)))

;; Public
;; ------

(defn lint
  [opts]
  (let [warnings (:warnings (eastwood/lint opts))
        packages (group-by :namespace-sym warnings)]
    (conj-if
      [:testsuites]
      (map to-testsuite (keys packages) (vals packages)))))

(defn run-from-cmdline
  [opts]
  (emit
    (sexp-as-element
      (lint opts))
    *out*)
  nil)

