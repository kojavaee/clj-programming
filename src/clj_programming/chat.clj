(ns clj-programming.chat)

(defrecord Message [sender text])

(def messages (ref ()))

; bad idea
(defn native-add-message [msg]
  (dosync (ref-set messages (cons msg @messages))))

(defn add-message [msg]
  (dosync (alter messages conj msg)))

;;commute是一种特殊的alter变体，允许更我并发
(defn add-message-commute
  [msg]
  (dosync (commute messages conj msg)))

;;为引用添加验证
(def validate-message-list
  (partial every? #(and (:sender %) (:text %))))

;;(ref initial-state options*)
;;:validator
;;:meta
(def messages (ref () :validator validate-message-list))

