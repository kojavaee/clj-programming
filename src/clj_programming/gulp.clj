(ns clj-programming.gulp
  (:import [java.io FileInputStream InputStream BufferedReader]))

(defn gulp [src]
  (let [sb (StringBuffer.)]
    (with-open [reader (-> src
                         FileInputStream.
                         FileInputStream.
                         BufferedReader.)]
      (loop [c (.read reader)]
        (if (neg? c)
          (str sb)
          (do
            (.append sb (char c))
            (recur (.read reader))))))))
