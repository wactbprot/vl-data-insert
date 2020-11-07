(ns vl-data-insert.utils
  ^{:author "wactbprot"
    :doc "Utils for data insert tasks."}
  (:require [clojure.string :as string]))


(defn ensure-vec
  "Ensures that `v` is a vector.

  Example:
  ```clojure
  (ensure-vec nil) ;!
  ;; nil
  (ensure-vec 1)
  ;; [1]
  (ensure-vec [1])
  ;; [1]
  ```"
  [v]
  (when v (if (vector? v) v [v])))

(defn vector-if
  "Makes the value `v` behind the keyword `kw` a vector if `v` is not
  nil."
  [m kw]
  (if (and (map? m) (keyword? kw))
    (if-let [v (kw m)]
      (assoc m kw (ensure-vec v))
      m)))

(defn replace-if
  "Replaces `v`alue of `k`ey in struct if `v`is not `nil`.

  Example:
  ```clojure
  (replace-if {:Type \"a\"} :Type \"b\")
  ;; {:Type \"b\"}
  ```
  "
  [m k v]
  (if (and (some? v) (keyword? k)) (assoc m k v) m))

(defn append-if
  "Appends `v` to the value of `k`. If `k` does not exist in `m`,
  `k [v]` is assoced.  If `k` does exist in `m`, `v` is conjed.

  Example:
  ```clojure
  (append-if {:Value [1 2 3]} :Value 4)
  ;; {:Value [1 2 3 4]}
  ```"
  [m k v]
  (if (and (some? v) (keyword? k))
    (let [new-v (ensure-vec v)]
      (if-let [old-v (k m)]
        (assoc m k (into [] (concat old-v new-v)))
        (assoc m k new-v)))
    m))

(defn path->kw-vec
  "Turns the path into a vector of keywords.

  ```clojure
  (path->kw-vec \"a.b.c\")
  ;; [:a :b :c]
  ```"
  [s]
  {:pre [(string? s)]}
  (into []
        (map
         keyword
         (string/split s (re-pattern "\\.")))))
