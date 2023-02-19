(ns util)

(defmacro async-doseq [[binding async-iterable] & body]
  `(let [async-iterator# ((js* "~{}[Symbol.asyncIterator]" ~async-iterable))]
     (fn handle-next# []
       (let [next# (.next async-iterator#)]
         (.then
          next#
          (fn [res#]
            (if (. res# -done)
              nil
              (do (let [~binding (. res# -value)]
                    ~@body)
                  (handle-next#)))))))))

(defmacro async-for [[binding async-iterable] & body]
  `(let [results# (transient [])]
     (-> (async-doseq [~binding ~async-iterable]
                      (conj! results# (do ~@body)))
         (.then (fn [_] (persistent! results#))))))
