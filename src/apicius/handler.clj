(ns apicius.handler
  (:require [ring.middleware.json :as middleware]
            [compojure.handler :as handler]
            [apicius.routes.cookbookapi :refer [cookbookapi-routes]]))

(defn init []
  (println "apicius is starting"))

(defn destroy []
  (println "apicius is shutting down"))

(def app
  (-> (handler/api cookbookapi-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
