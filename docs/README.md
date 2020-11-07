# documentation

* [API](./api)
* [coverage](./coverage)
* [namespaces](./graph.png)

# installation


[![Clojars Project](https://img.shields.io/clojars/v/org.clojars.wactbprot/vl-data-insert.svg)](https://clojars.org/org.clojars.wactbprot/vl-data-insert)



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
