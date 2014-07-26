(ns grafix.core
  (:import (org.lwjgl.opengl Display DisplayMode GL11)
           (org.lwjgl.util.glu GLU)
           (org.lwjgl LWJGLException)))


(defn init-gl []
  (GL11/glClearColor 0.0 0.5 0.5 0.0)
  )

(defn render-gl []
  (GL11/glClear (bit-or GL11/GL_COLOR_BUFFER_BIT GL11/GL_DEPTH_BUFFER_BIT))
  ;; Render GL here
  )

(defn run []
  (try
    (do
      (Display/setDisplayMode (DisplayMode. 800 600))
      (Display/setTitle "Hello GL")
      (Display/create))
    (catch LWJGLException e ""))
  (init-gl)
  (while (not (Display/isCloseRequested))
    (render-gl)
    (Display/update))
  (Display/destroy))

(defn -main [& args]
  (println "OpenGL demonstration and tutorials")
  (.start (Thread. run)))

(.start (Thread. run))
