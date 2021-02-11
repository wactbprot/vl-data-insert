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
  [struct m]
  (let [{t :Type v :Value u :Unit n :N s :SdValue} m]
    (->
     (-> struct
         (u/replace-if (u/check-kw m :Type) t)
       (u/replace-if (u/check-kw m :Unit) u))
     (u/append-if (u/check-kw m :Value) v)
     (u/append-if (u/check-kw m :SdValue) s)
     (u/append-if (u/check-kw m :N) n))))

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
  "Stores the result `x`(ensured to be a map `m`) in the given
  `d`ocument under `p`ath. If `m` contains `:Type` and `:Value` `m`
  is [[fit-in-struct]] and the structure `s` is assumed to be a
  `vector`. Other cases (e.g. merge in `:AuxValues`) are straight
  forward (see [[vl-data-insert/test/cmp/doc_test.clj]] for details)."
  [d x p]
  (let [[m v] (u/ensure-map x (u/path->kw-vec p))]
    (if (and (contains? m :Type) (contains? m :Value))
      (if-let [s (get-in d v)]
        (assoc-in d v (fit-in-struct s m))
        (assoc-in d v [(vector-vals m)]))
      (if-let [s (get-in d v)]
        (assoc-in d v (merge s m))
        (assoc-in d v m)))))

(defn store-results
  "Takes a vector of maps. Calls `store-result` on each map.

  Example:
  ```clojure
  (def p \"Calibration.Measurement.Values.Pressure\")
  (def m {:Type    \"a\"
        :Unit    \"b\"
        :Value   [0]
        :SdValue [0]
        :N       [1]})
  
  (def d {:Calibration
         {:Measurement
          {:Values
           {:Pressure
           [{:Type    \"a\"
            :Unit    \"b\"
            :Value   [0]
            :SdValue [0]
            :N       [1]}]}}}})
  
  (store-results d [m m m m] p)

  ;; =>
  ;;   {:Calibration
  ;;    {:Measurement
  ;;     {:Values
  ;;      {:Pressure
  ;;       [{:Type \"a\",
  ;;         :Unit \"b\",
  ;;         :Value [0 0 0 0 0],
  ;;         :SdValue [0 0 0 0 0],
  ;;         :N [1 1 1 1 1]}]}}}}
  ```"
  [doc v p]
  (reduce (fn [doc m] (store-result doc m p)) doc v))
