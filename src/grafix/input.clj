(ns grafix.input
  (:import (org.lwjgl.input Keyboard Mouse)))

(defn poll-keyboard []
  (while (Keyboard/next)
    (println (str "A key was pressed: " (Keyboard/getEventKey)))))

(defn poll-mouse []
  (when (Mouse/isButtonDown 0)
    (println (str "Mouse 1 pressed (" (Mouse/getX) "," (Mouse/getY) ")"))))

(defn poll-input []
  (do
   (poll-mouse)
   (poll-keyboard)))
