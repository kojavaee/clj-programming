(ns clj-programming.introduction)

(defn blank?
  "string is blank?"
  [str]
  (every? #(Character/isWhitespace %) str))

(def visitors (atom #{}))

(defn hello
  "Writes to hello message to *out*.Calls you by username.
   Knows if you have bean here brefore."
  [username]
  (swap! visitors conj username)
  (str "Hello, " username))