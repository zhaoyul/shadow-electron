(ns app.renderer.user-index.views
  (:require [re-frame.core :as re-frame]
            [app.renderer.user-index.subs :as subs]
            [app.renderer.routes :as routes]
            [app.renderer.events :as events]))


(defn users-index []
  (let [users @(re-frame/subscribe [::subs/users])]
    [:div
     [:h1 "User List"]
     (map (fn [user] [:div {:key (:id user)
                            :on-click #(re-frame/dispatch [::events/navigate [:user-view :id (:id user)]])} (:name user)]) users)]))

(defmethod routes/panels :users-index-panel [] [users-index])
