(ns apicius.routes.cookbookapi
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [apicius.models.cookbook :as cookbook]))

(defn get-slash []
  ;; FIXME: this should be different? Look at results of cookbookapi...
  (response (map cookbook/fetch (cookbook/list-all))))

(defn get-all []
  (response (map cookbook/fetch (cookbook/list-all))))

(defn get-cookbook
  ([cookbook]
    (response (cookbook/fetch cookbook)))
  ([cookbook version]
    (response (cookbook/fetch cookbook version))))

(defn get-cookbook-file [cookbook version]
  ;; this should actually end up returning an application/x-gzip...
  (response (cookbook/fetch-file cookbook version)))

(defn post-cookbook [cookbook]
  (response (cookbook/create cookbook)))

(defroutes cookbookapi-routes
  (context "/cookbooks" [] (defroutes cookbooks-routes
    (GET "/" [] (get-slash))
    (GET "/all" [] (get-all))
    (context "/:cookbook" [cookbook] (defroutes cookbook-routes
      (GET  "/" [] (get-cookbook cookbook))
      (POST "/" [] (post-cookbook cookbook))
      (context "/versions" [] (defroutes versions-routes
        ; (GET "/" [] (get-cookbook cookbook))
        (GET "/latest" [] (get-cookbook cookbook :latest))
        (GET "/:version" [version] (get-cookbook cookbook version))))))
    (context "/files" [] (defroutes files-routes
      (context "/:cookbook" [cookbook] (defroutes files-cookbook-routes
        ; (GET "/" [] (get-cookbook-file cookbook))
        (GET "/:version" [version] (get-cookbook-file cookbook version))))))))
    ; (GET "/search" [] )
  (route/not-found "Not Found"))
