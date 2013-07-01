(ns clj-programming.concurrency)

(def counter (ref 0))

;;不应该使用commute来更新计数器。commute返回的是它被调用的那一刻，计算器的事务内值，
;;但重新排序可能会导致事务结束，其值与之前并不相同
;;一般情况下，你应该首选alter而不是commute.
(defn next-counter [] (dosync (alter counter inc)))

