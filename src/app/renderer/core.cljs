(ns app.renderer.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [app.renderer.events :as events]
   [app.renderer.routes :as routes]
   [app.renderer.views :as views]
   [app.renderer.config :as config]
   [app.renderer.user-index.views]
   [app.renderer.user-view.view]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app-container")]
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (routes/start!)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
