(ns app.renderer.core
  (:require [reagent.core :refer [atom]]
            [reagent.dom :as rd]))

(defonce sdk (js/require "@jitsi/electron-sdk"))

(def options
  #js
  {:roomName "JitsiMeetAPIExample",
   :width 700,
   :height 700,
   :parentNode (js/document.querySelector "#app-container"),
   :lang "en"})

(def domain "meet.jit.si")

(def api (new js/JitsiMeetExternalAPI domain options))


(defonce state (atom 0))


(defn root-component []
  [:div
   [:div.logos
    [:img.electron {:src "img/electron-logo.png"}]
    [:img.cljs {:src "img/cljs-logo.svg"}]
    [:img.reagent {:src "img/reagent-logo.png"}]]
   [:button
    {:on-click #(swap! state inc)}
    (str "Clicked " @state " times")]])

(defn ^:dev/after-load start! []
  (rd/render
   [root-component]
   (js/document.getElementById "app-container")))
