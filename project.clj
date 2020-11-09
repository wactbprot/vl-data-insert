(defproject org.clojars.wactbprot/vl-data-insert "0.1.3"
  :description "Functions to insert vaclab style data in vaclab style database documents."
  :url "https://github.com/wactbprot/vl-data-insert"
  :dependencies [[org.clojure/clojure  "1.10.0"]
                 [clojang/codox-theme  "0.2.0-SNAPSHOT"]]
  :plugins [[lein-cloverage  "1.1.2"]
            [lein-codox      "0.10.7"]]
  :codox {:themes [:clojang]
          :metadata {:doc/format :markdown}
          :source-uri "https://github.com/wactbprot/cmp/blob/master/{filepath}#L{line}"}
  :repl-options {:init-ns vl-data-insert.core})
