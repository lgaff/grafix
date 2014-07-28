(ns grafix.input
  (:import (org.lwjgl.input Keyboard Mouse))
  (:require [grafix.draw :as draw]))

(defn init-input []
  (def input-state (ref {:last-x 0
                         :last-y 0})))

(defn poll-keyboard []
  (while (Keyboard/next)
    (println (str "A key was pressed: " (Keyboard/getEventKey) Keyboard/KEY_U))

    (cond
     (= (Keyboard/getEventKey) Keyboard/KEY_U) (draw/set-pen :red 0.01)
     (= (Keyboard/getEventKey) Keyboard/KEY_J) (draw/set-pen :red -0.01)
     (= (Keyboard/getEventKey) Keyboard/KEY_I) (draw/set-pen :green 0.01)
     (= (Keyboard/getEventKey) Keyboard/KEY_K) (draw/set-pen :green -0.01)
     (= (Keyboard/getEventKey) Keyboard/KEY_O) (draw/set-pen :blue 0.01)
     (= (Keyboard/getEventKey) Keyboard/KEY_L) (draw/set-pen :blue -0.01))))

(defn poll-mouse []
  (while (Mouse/next)
    (if (Mouse/getEventButtonState)
      (if (= (Mouse/getEventButton) 0)
        (do
          (println (format "Left button pressed at %d, %d" (Mouse/getEventX) (Mouse/getEventY)))
          (dosync (ref-set input-state
                           (assoc @input-state
                             :last-x (Mouse/getEventX)
                             :last-y (Mouse/getEventY))))))
      (if (= (Mouse/getEventButton) 0)
        (do
          (println (format "Left button released at %d, %d, delta is %d, %d"
                           (Mouse/getEventX)
                           (Mouse/getEventY)
                           (- (Mouse/getEventX) (:last-x @input-state))
                           (- (Mouse/getEventY) (:last-y @input-state))))
          (draw/add-geometry draw/rectangle (into [] (concat[(:last-x @input-state)
                                                             (:last-y @input-state)
                                                             (Mouse/getEventX)
                                                             (Mouse/getEventY)]
                                                            (draw/pen-color)))))))))
(defn poll-input []
  (do
   (poll-mouse)
   (poll-keyboard)))
