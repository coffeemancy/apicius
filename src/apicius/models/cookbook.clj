(ns apicius.models.cookbook
  (:require [clojure.string :as string]))

(defn list-versions [cookbook]
  ["0.0.1" "0.1.0" "1.0.0"])

(defn list-all []
  ["foo" "bar"])

(defn num-semvar [semvar]
  ;; Converts semvar to number
  (let [parts (mapv read-string (string/split semvar #"\." 3))]
    (+ (* 100 (parts 0)) (* 10 (parts 1)) (parts 2))))

(defn fetch
  ([cookbook]
    {:name cookbook
     :category "COOKBOOK_MAINTAINER"
     :updated_at "2009-09-26T00:51:36Z"
     :maintainer "COOKBOOK_MAINTAINER"
     :latest_version "?"
     :external_url nil
     :versions (list-versions cookbook)
     :description "COOKBOOK_DESCRIPTION"
     :average_rating nil
     :created_at "2009-09-26T00:51:36Z"})
  ([cookbook version]
    {:license "Restricted"
     :updated_at "2009-09-26T00:51:36Z"
     :tarball_file_size nil
     :version (if (= version :latest)
                    ;; latest version
                    (max num-semvar (list-versions cookbook))
                    version)
     :average_rating nil
     :cookbook (str "https://apicius.dyndns.com/cookbooks/" cookbook)
     :created_at "2009-09-26T00:51:36Z"
     :file (str "files/" cookbook "-" version ".tgz")}))

(defn fetch-file [cookbook version]
  {:get "cookbook-file"
   :cookbook cookbook
   :version version})

(defn create [cookbook]
  {:post (str "cookbook: " cookbook)})
