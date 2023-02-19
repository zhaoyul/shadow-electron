(ns app.renderer.core
  (:require [reagent.core :refer [atom]]
            ["react-split" :as Split]
            ["echarts-for-react$default" :as ReactECharts]
            [util :refer [async-doseq  async-for]]
            [shadow.cljs.modern :refer (js-await)]
            [reagent.dom :as rd]))

(def zmq (js/require "zeromq"))




(comment
  (defn server []
    (let [server (new (. zmq -Pair))]
      (-> (.bind server "tcp://*:3021")
          (.then (async-doseq [ms server]
                              (js/console.log ms)
                              )))))


  (server)
  (defn client []
    (let [client (new (. zmq -Pair))]
      (def c client)
      (.connect client "tcp://192.168.1.104:5556")
      (.send client "{\"CommandName\":\"Start\",\"Params\":null}")
      (while true
        (-> (.receive client)
            (.then
             (fn [msg]
               (js/console.log msg)
               (def msg msg)))
            (.catch (fn [failure]
                      (prn [:oh-oh failure])))))))
  )



(defonce state (atom 0))
(defn root-component []
  [:div {:style {:height 300}}
   [:> Split  {:className "split"
               :sizes (clj->js [25, 50, 25])
               :minSize 50
               :expandToMin true
               :gutterSize 10
               ;;:onDrag js/console.log
               :onDragEnd js/console.log
               :gutterAlign "center"
               :snapOffset 20
               :dragInterval 10
               ;;:direction :vertical
               :cursor "col-resize"}
    [:div
     [:> ReactECharts {:theme "dark"
                       :option
                       {:xAxis
                        {:type "category", :data ["Mon" "Tue" "Wed" "Thu" "Fri" "Sat" "Sun"]},
                        :yAxis {:type "value"},
                        :series [{:data [150 230 224 218 135 147 260], :type "line"}]}
                       }]]
    [:div "b"]
    [:div "b"]]]

  )

(defn ^:dev/after-load start! []
  (rd/render
   [root-component]
   (js/document.getElementById "app-container")))
