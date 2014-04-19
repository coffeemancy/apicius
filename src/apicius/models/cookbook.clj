(ns apicius.models.cookbook
  (:require [clojure.string :as string])
  (:require [apicius.modesl.db :as db]))

(defn latest-version [cookbook]
  (apply max-key num-semvar (list-versions cookbook)))

(defn list-versions [cookbook]
  (db/lookup-versions cookbook))

(defn list-all []
  ["foo" "bar"])

(defn num-semvar [semvar]
  "Converts semvar to a number"
  (let [parts (mapv read-string (string/split semvar #"\." 3))]
    (+ (* 100 (parts 0)) (* 10 (parts 1)) (parts 2))))

(defn fetch
  ([cookbook]
    (let [v (latest-version cookbook)
          ])
    {:name cookbook
     :category "COOKBOOK_MAINTAINER"
     :updated_at "2009-09-26T00:51:36Z" ; search for this
     :maintainer "COOKBOOK_MAINTAINER"
     :latest_version (latest-version cookbook)
     :external_url nil
     :versions (list-versions cookbook)
     :description "COOKBOOK_DESCRIPTION"
     :average_rating nil
     :created_at "2009-09-26T00:51:36Z"}) ; search for this
  ([cookbook version]
    (let [cb (db/lookup cookbook version)]
      {:license (:license cb)
       :updated_at (:updated_at cb)
       :tarball_file_size (:tarball_file_size cb)
       :version (if (= version :latest)
                    (latest-cookbook cookbook)
                    version)
       :average_rating (:average_rating cb)
       :cookbook (str "https://apicius.dyndns.com/cookbooks/" cookbook)
       :created_at (:created_at cb)
       :file (:file cb)})))

(defn fetch-file [cookbook version]
  {:get "cookbook-file"
   :cookbook cookbook
   :version version})

(defn create [cookbook]
  {:post (str "cookbook: " cookbook)})
