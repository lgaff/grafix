(ns grafix.core
  (:import (org.lwjgl.opengl Display DisplayMode GL11)
           (org.lwjgl.util.glu GLU)
           (org.lwjgl LWJGLException))
  (:require [grafix.input :as input]))

(def MAX_FPS 60)

(defn init-gl []
  (def gl-state (ref {:time (/ (System/nanoTime) 1000000)
                      :fps 0.0}))
  (GL11/glClearColor 0.0 0.5 0.5 0.0)
  (GL11/glMatrixMode GL11/GL_PROJECTION)
  (GL11/glLoadIdentity)
  (GL11/glOrtho 0 800 0 600 1 -1)
  (GL11/glMatrixMode GL11/GL_MODELVIEW))

(defn get-time []
  (/ (System/nanoTime)
     1000000))

(defn time-delta []
  (int (- (get-time) (:time @gl-state))))


(defn render-gl []
    (GL11/glClear (bit-or GL11/GL_COLOR_BUFFER_BIT GL11/GL_DEPTH_BUFFER_BIT))
    ;; Render GL here
  (Display/setTitle (format "FPS: %.2f" (:fps @gl-state)))
    (GL11/glColor3f 0.8 0.3 0.3)
    (GL11/glBegin GL11/GL_POLYGON)
    (do
      (GL11/glVertex2f 100 100)
      (GL11/glVertex2f 300 100)
      (GL11/glVertex2f 300 300)
      (GL11/glVertex2f 100 300)
      (GL11/glVertex2f 50 200))
    (GL11/glEnd))

(defn update-fps []
  (if (> (time-delta) 0)
    (float (/ 1000 (time-delta)))
    0.0))
(defn run []

  (try
    (do
      (Display/setDisplayMode (DisplayMode. 800 600))
      (Display/create))
    (catch LWJGLException e ""))
  (init-gl)
  (while (not (Display/isCloseRequested))
    (render-gl)
    (input/poll-input)
    (Display/sync MAX_FPS)
    (Display/update)
    (dosync (ref-set gl-state
                     (assoc @gl-state
                       :time (get-time)

                       :fps (update-fps)))))
  (Display/destroy))

(defn -main [& args]
  (println "OpenGL demonstration and tutorials")
  (.start (Thread. run)))
