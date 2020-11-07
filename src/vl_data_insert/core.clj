(ns vl-data-insert.core
  ^{:author "wactbprot"
    :doc "Inserts data into documents.  This documents may be
          calibration documents but also measurement docs."}
  (:require [vl-data-insert.utils :as u]
            [clojure.string :as string]))

;;------------------------------
;; data to doc
;;------------------------------
(defn vector-vals
  "Ensures that the values of `:Value`,`:SdValue` and `:N` are
  vectors."
  [m]
  (-> m
      (u/vector-if :Value)
      (u/vector-if :SdValue)
      (u/vector-if :N)))

(defn append-and-replace
  "Append `:Value`, `:SdValue` and `:N` if present. Relaces `:Type` and
  `:Unit`."
  [struct {t :Type v :Value u :Unit n :N s :SdValue}]
  (->
   (-> struct
       (u/replace-if :Type t)
       (u/replace-if :Unit u))
   (u/append-if :Value v)
   (u/append-if :SdValue s)
   (u/append-if :N n)))

(defn fit-in-struct
  "Fits `m` into the given structure `s`. Function looks up the
  `:Type` of `m`. If a structure with the same `:Type` exist
  [[append-and-replace]] is called."
  [s m]
  (if-let [t (:Type m)]
    (let [same-type? (fn [x] (= (:Type x) t))
          idx?       (fn [i x] (when (same-type? x) i))]
      (if-let [idx (first (keep-indexed idx? s))]
        (assoc s idx (append-and-replace (nth s idx) m))
        (conj s (vector-vals m))))))

(defn store-result
  "Stores the result map `m` in the given `doc`ument under `p`ath. If
  `m` contains `:Type` and `:Value` `m` is [[fit-in-struct]] and the
  structure `s` is assumed to be a `vector`. Other cases (e.g. merge
  in `:AuxValues`) are straight
  forward (see [[vl-data-insert/test/cmp/doc_test.clj]] for details)."
  [doc m p]
  (let [v (u/path->kw-vec p)]
    (if (and (:Type m) (:Value m))
      (if-let [s (get-in doc v)]
        (assoc-in doc v (fit-in-struct s m))
        (assoc-in doc v [(vector-vals m)]))
      (if-let [s (get-in doc v)]
        (assoc-in doc v (merge s m))
        (assoc-in doc v m)))))

(defn store-results
  "Takes a vector of maps. Calls `store-result` on each map."
  [doc res path]
  (reduce 
   (fn [doc m] (store-result doc m path))
   doc res))
