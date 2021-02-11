(ns vl-data-insert.utils
  ^{:author "wactbprot"
    :doc "Utils for data insert tasks."}
  (:require [clojure.string :as string]))

(defn check-kw [m kw] (when (contains? m kw) kw))

(defn ensure-map
  "Ensures `x`to be a map. If `x` is a value a map is constucted from
  the last keyword and the value.

  Example:
  ```clojure
  (ensure-map 10 [:a :b :c])
  ;; =>
  ;; [{:c 10} [:a :b]]
  (ensure-map {:d 10} [:a :b :c])
  ;; =>
  ;; [{:d 10} [:a :b :c]]
  ```"
  [x v]
  (if (map? x)
    [x v]
    [{(last v) x} (into [] (butlast v))]))

(defn ensure-vec
  "Ensures that `v` is a vector even if `v` is `nil`.

  Example:
  ```clojure
  ;; important:
  (ensure-vec nil) 
  ;;=> [nil]
  ;; ^^ because:
  (concat [1 2] nil)
  ;; => (1 2) ; but we need:
  (concat [1 2] [nil])
  ;; => (1 2 nil)
  ;; to ensure that all
  ;; Value-vectors keep the same length
  (ensure-vec 1)
  ;; [1]
  (ensure-vec [1])
  ;; [1]
  ```"
  [v]
  (if (vector? v) v [v]))

(defn vector-if
  "Makes the value `v` of keyword `kw` a vector also if `v` is  `nil`."
  [m kw]
  (if (and (map? m) (keyword? kw))
    (if-let [v (kw m)] (assoc m kw (ensure-vec v)) m)))

(defn replace-if
  "Replaces `v`alue of `k`ey in struct if `v`is not `nil`.

  Example:
  ```clojure
  (replace-if {:Type \"a\"} :Type \"b\")
  ;; {:Type \"b\"}
  ```
  "
  [m k v]
  (if (keyword? k) (assoc m k v) m))

(defn append-if
  "Appends `v` to the value of `k`. If `k` does not exist in `m`,
  `k [v]` is assoced.  If `k` does exist in `m`, `v` is conjed.

  Example:
  ```clojure
  (append-if {:Value [1 2 3]} :Value 4)
  ;; {:Value [1 2 3 4]}
  ```"
  [m k v]
  (if (keyword? k)
    (let [new-v (ensure-vec v)]
      (if-let [old-v (k m)]
        (assoc m k (into [] (concat old-v new-v)))
        (assoc m k new-v)))
    m))

(defn path->kw-vec
  "Turns the path into a vector of keywords.

  Example:
  ```clojure
  (path->kw-vec \"a.b.c\")
  ;; [:a :b :c]
  ```"
  [s]
  {:pre [(string? s)]}
  (mapv keyword (string/split s (re-pattern "\\."))))
