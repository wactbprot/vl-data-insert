# documentation

* [API](./api)
* [coverage](./coverage)

# installation

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.wactbprot/vl-data-insert.svg)](https://clojars.org/org.clojars.wactbprot/vl-data-insert)


In `project.clj` add:

```clojure
{:dependencies [[org.clojure/clojure                  "1.10.1"]
                [org.clojars.wactbprot/vl-data-insert "0.1.1"]]
}
```

Require in namespace with:

```clojure
(ns demo.insert
    (:require [vl-data-insert.core :as i]))

```

Example usage:

```clojure
(def p "Calibration.Measurement.Values.Pressure")
(def m {:Type    "a"
        :Unit    "b"
        :Value   [0]
        :SdValue [0]
        :N       [1]})

(def d {:Calibration
         {:Measurement
          {:Values
           {:Pressure
           [{:Type    "a"
            :Unit    "b"
            :Value   [0]
            :SdValue [0]
            :N       [1]}]}}}})

(i/store-results d [m m m m] p)

;; =>
;;   {:Calibration
;;    {:Measurement
;;     {:Values
;;      {:Pressure
;;       [{:Type "a",
;;         :Unit "b",
;;         :Value [0 0 0 0 0],
;;         :SdValue [0 0 0 0 0],
;;         :N [1 1 1 1 1]}]}}}}
```


## Leiningen on Ubuntu 

```shell
sudo apt install leiningen
```

##  Leiningen on openSUSE (LEAP 15)

```shell
zypper ar https://download.opensuse.org/repositories/devel:/languages:/clojure/openSUSE_Leap_15.1/devel:languages:clojure.repo
zypper ref devel_languages_clojure
zypper in  leiningen
```

# devel

All devel commands have to be executed
in the root directory of *vl-data-insert*.

```shell
$ cd path/to/vl-data-insert
```

## documentation

(re)generate documentation

```shell
$ lein codox
```

## tests

```shell
$ lein test
```

### Run tests from REPL

Example `utils-tests`:

```clojure
(ns cmp.utils-test) 
(use 'clojure.test)
(run-tests)
```

## code coverage

```shell
$ lein cloverage
```
