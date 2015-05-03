
(ns owsy.eastwood-junit.core
  (:require [clojure.data.xml :refer [emit-str sexp-as-element]]
            [eastwood.lint :as eastwood]))

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
  (let [suite [:testsuite {:package nss}]
        cases (map to-testcase warnings)]
    (if (empty? cases)
      suite
      (apply conj suite (vec cases)))))

(defn- xml-for
  [opts]
  (let [suite [:testsuites]
        warnings (:warnings (eastwood/lint opts))
        packages (group-by :namespace-sym warnings)
        suites (map to-testsuite (keys packages) (vals packages))]
    (if (empty? suites)
      suite
      (apply conj suite (vec suites)))))

;; Public
;; ------

(defn lint
  [opts]
  (sexp-as-element
    (xml-for opts)))

(defn run-from-cmdline
  [opts]
  (println
    (emit-str
      (lint opts))))

