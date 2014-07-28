(ns grafix.draw
  (:import (org.lwjgl.opengl GL11)))

(defn init-draw []
  (def draw-state (ref {:pen [0.0 0.0 0.0]}))
  (def geometry (ref [])))

(defn add-geometry [type s]
  (dosync (alter geometry conj  (into [] (concat [type (gensym)] s)))))

(defn pen-color []
  (:pen @draw-state))

(defn set-pen [color val]
  (let [[red green blue] (:pen @draw-state)]
    (println "Updating pen" color val [red green blue])
    (dosync (ref-set draw-state
                     (assoc @draw-state
                       :pen (cond
                             (= color :red)   [(mod (+ red val) 1.0) green blue]
                             (= color :green) [red (mod (+ green val) 1.0) blue]
                             (= color :blue)  [red green (mod (+ blue val) 1.0)]))))))

(defn rectangle [name x y dx dy r g b]
    (GL11/glColor3f r g b)
    (GL11/glBegin GL11/GL_TRIANGLE_STRIP)
    (do
      (GL11/glVertex2f x y)
      (GL11/glVertex2f dx
                       y)
      (GL11/glVertex2f x
                       dy)
      (GL11/glVertex2f dx
                       dy))
    (GL11/glEnd))

(defn square [name x y w r g b]
  (rectangle name x y w w r g b))

(defn render-geometry []
  (doseq [s @geometry]
          (apply (first s) (rest s))))
