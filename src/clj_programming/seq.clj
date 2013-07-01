(ns clj-programming.seq
  (:import [java.io File]))

(defn whole-numbers
  "全部整数序列"
  []
  (iterate inc 1))

;;查看最近被修改的文件
(defn minutes-to-millis [mins] (* mins 1000 60))

(defn recently-modified? 
  [file]
  (> (.lastModified file)))