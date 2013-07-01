(ns clj-programming.exploring)

;;clojure的函数
;;clojure 函数强调元数（arity）,也就是它期望获得的参数数量
;;如果参数列表包含一个&，就可以创建一个可变元数的函数，
;;clojure 会把所有剩余的参数都放进一个序列中，并绑定到&符号后面的那个名称eg:date
;;为不同的元数编写函数实现有点类似于“多态”，clojure能够做到的远不止于此
(defn greeting
  ""
  [username]
  (str "Hello, " username))

(defn greeting
  ([] (greeting "word"))
  ([username] (str "Hello, " username)))

(defn date [p-1 p-2 & chaperones]
  (str p-1 " and " p-2 " went out with " (count chaperones) " chaperones."))

;;使用clojure的匿名函数的至少三个原因
;;1这是一个很简短且不言自明的函数，如果给它取名字的话，不会令可读性增强，返而使得代码更难以阅读
;;2这是一个仅在别的函数内部使用的函数，需要的是局部名称，而非顶级绑定
;;3这个函数是在别的函数中被创建的，其目的是为了隐藏某些数据.
(defn indexable-words 
  [text]
  (let [indexable-words? (fn [w] (> (count w) 2))]
    (filter indexable-words? (clojure.string/split text #"\W+"))))

;;绑定
(defn square-corners 
  [bottom left size]
  (let [top (+ bottom size)
        right (+ left size)]
    [[bottom left] [top left] [top right] [bottom right]]))

;;解构
;;(let [[_ _ z] [1 2 3]] z),_是一个习惯用法用来表示：“我们对这个绑定毫不关心”
;;:as字句允许你绑定整个闭合结构。
(defn greet-author-1
  [author]
  (println "Hello, " (:first-name author)))

(defn greet-author-2
  [{fname :first-name}]
  (println "Hello, " fname))

(defn ellipsize 
  [words]
  (let [[w1 w2 w3] (clojure.string/split words #"\s+")]
    (clojure.string/join " " [w1 w2 w3 "..."])))

