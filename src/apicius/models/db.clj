(ns apicius.models.db
  (:require [korma.core :refer :all]))

(defdb cookbooks (sqlite3 {:db "cookbooks"}))

(declare cookbooks
  cookbook
    cb-name versions category description maintainer
  version
    created-at updated-at file tarball-file-size)

;; table of cookbooks
(defentity cookbooks
  (database db)
  (has-many cookbook))

;;
(defentity cookbook
  (database db)
  ;; for lookups
  (has-one cb-name)
  (has-many versions)
  ;; data get back
  (has-one category)
  (has-one description)
  (has-one maintainer))

(defentity versions
  (database db)
  ;; for lookups
  (has-one version)
  ;; data to get back
  (has-one created-at)
  (has-one updated-at)
  (has-one file)
  (has-one tarball-file-size))

(defn lookup-versions [cookbook]
  ["0.0.1" "0.1.0" "1.0.0"])

(defn lookup [cookbook version]
  {:license "Restricted"
   :updated_at "2009-09-26T00:51:36Z"
   :tarball_file_size nil
   :average_rating nil
   :created_at "2009-09-26T00:51:36Z"
   :file (str "files/" cookbook "-" version ".tgz")})
