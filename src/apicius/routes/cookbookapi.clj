(ns apicius.routes.cookbookapi
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]))

(defn get-slash []
  (response {:get "slash"}))

(defn get-all []
  (response {:get "all"}))

(defn get-cookbook [cookbook]
  (response {:get "cookbook" :cookbook cookbook}))

(defn get-cookbook-version [cookbook version]
  (response {:get "cookbook-version" :cookbook cookbook :version version}))

(defn get-cookbook-file [cookbook version]
  (response {:get "cookbook-file" :cookbook cookbook :version version}))

(defroutes cookbookapi-routes
  (GET "/" [] (get-slash))
  (GET "/all" [] (get-all))
  (context "/:cookbook" [cookbook] (defroutes cookbook-routes
    (GET  "/" [] (get-cookbook cookbook))
    (context "/versions" [] (defroutes versions-routes
      ; (GET "/" [] (get-cookbook-version cookbook))
      (GET "/:version" [version] (get-cookbook-version cookbook version))))))
  (context "/files" [] (defroutes files-routes
    ; (GET "/" [] (get-cookbook-file))
    (context "/:cookbook" [cookbook] (defroutes files-cookbook-routes
      ; (GET "/" [] (get-cookbook-file cookbook))
      (GET "/:version" [version] (get-cookbook-file cookbook version))))))
  (route/not-found "Not Found"))