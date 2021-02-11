# vl-data-insert

A Clojure library to insert vaclab style data in vaclab style database documents.

[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.wactbprot/vl-data-insert.svg)](https://clojars.org/org.clojars.wactbprot/vl-data-insert)

See [the github.io page of vl-data-insert](https://wactbprot.github.io/vl-data-insert/).

## Usage

In `project.clj` add:

```clojure
{:dependencies [[org.clojure/clojure                  "1.10.2"]
                [org.clojars.wactbprot/vl-data-insert "0.2.1"]]
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

## License

Copyright 2021 wactbprot

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

