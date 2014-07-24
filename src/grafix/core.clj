(ns grafix.core
  (:import (org.lwjgl.opengl Display DisplayMode GL11)
           (org.lwjgl.util.glu GLU)
           (org.lwjgl LWJGLException)))


(defn init-window
  [width height title]
  (def globals (ref {:width width
                     :height height
                     :title title
                     :angle 0.0
                     :last-time (System/currentTimeMillis)}))
  (try
    (Display/setDisplayMode (DisplayMode. width height))
    (Display/setTitle title)
    (Display/create)
    (catch LWJGLException e (.getMessage e))))

(defn init-gl
  []
  (println "OpenGL version:" (GL11/glGetString GL11/GL_VERSION))
  (GL11/glClearColor 0.0 0.0 0.0 0.0)
  (GL11/glMatrixMode GL11/GL_PROJECTION)
  (GLU/gluOrtho2D 0.0 (:width @globals)
                  0.0 (:height @globals))
  (GL11/glMatrixMode GL11/GL_MODELVIEW))

(defn run
  []
  (init-window 800 600 "Grafix demos")
  (init-gl)
  (while (not (Display/isCloseRequested))
    ;; TODO: Display update goes here.
    (Display/update)
    (Display/sync 60))
  (Display/destroy))

(defn -main [& args]
  (println "OpenGL demonstration and tutorials")
  (run))

