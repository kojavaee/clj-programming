(ns clj-programming.functional)

;;在编写Clojure程序时，对任何大小会变化的序列和任何大型的序列，你都应该优先选择惰性序列而不是loop/recur
(defn stack-consuming-fibo
  "糟糕的实现"
  [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (stack-consuming-fibo (- n 1)
               (stack-consuming-fibo (- n 2))))))

(defn tail-fibo
  "尾递归"
  [n]
  (letfn [(fib [current next n]
            (if (zero? n)
              current
              (fib next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(defn recur-fibo
  "recur不用消耗stack空间"
  [n]
  (letfn [(fib 
            [current next n]
            (if (zero? n)
              current
              (recur next (+ current next) (dec n))))]
    (fib 0N 1N n)))

(defn lazy-seq-fibo
  ([]
    (concat [0 1] (lazy-seq-fibo 0N 1N)))
  ([a b]
    (let [n (+ a b)]
      (lazy-seq
        (cons n (lazy-seq-fibo b n))))))

(defn fibo
  []
  (map first (iterate (fn [[a b]] [b (+ a b)]) [0N 1N])))

;;聊聊变现
(def lots-of-fibs (take 100000000 (fibo)))

;;持有了头部（应该避免）
(def head-fibo (lazy-cat [0N 1N] (map + head-fibo (rest head-fibo))))

(defn counts-head-pairs
  "计算硬币向上的情况"
  [coll]
  (loop [cnt 0 coll coll]
    (if (empty? coll)
      cnt
      (recur (if (= :h (first coll) (second coll))
               (inc cnt)
               cnt)
        (rest coll)))))

(def ^{:doc "count itmes matching a filter"}
  count-if (comp count filter))

(defn count-runs
  "Count runs of length n where pred is true in coll."
  [n pred coll]
  (count-if #(every? pred %) (partition n 1 coll)))

;(count-runs 2 #(= % :h) [:h :t :t :h :h :h])

;;FP 函数编程的6条规则
;;1.避免直接递归
;;2.当产生的是标量，或乾是体积小还数量固定的序列时，你可以使用recur
;;3.当产生的个头大，或是大小可变的序列时，让它成为惰性的，而不要用递归。
;;4.小心不要让一个惰性序列变现的太多，多的超出你的需要。
;;5.享悉序库。
;;6.细分。
(defn by-pairs
  "[:t :h :h :t] => [[:t :h] [:h :h] [:h :t]]"
  [coll]
  (let [result []
        el (take 2 coll)]
    (if (= (count coll) 2)
      (cons el result)
      (cons el (by-pairs (rest coll))))))

(defn by-pairs
  [coll]
  (let [take-pair (fn [c]
                    (when (next c) (take 2 c)))]
    (lazy-seq 
      (when-let [pair (seq (take-pair coll))]
        (cons pair (by-pairs (rest coll)))))))

;测试633{/#13/}DaYingJia{/#13/}75540279
