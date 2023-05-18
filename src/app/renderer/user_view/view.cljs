(ns app.renderer.user-view.view
  (:require [re-frame.core :as re-frame]
            [app.renderer.routes :as routes]
            [app.renderer.user-view.subs :as subs]
            [app.renderer.subs :as route-subs]))

(defn user-view []
  (let [route-params @(re-frame/subscribe [::route-subs/route-params])
        user @(re-frame/subscribe [::subs/user (:id route-params)])]
    [:div (str "The selected user is " (:name user))]))

(defmethod routes/panels :user-view-panel [] [user-view])
