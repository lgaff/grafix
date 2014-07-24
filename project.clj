(defproject grafix "0.1.0-SNAPSHOT"
  :description "Testing the waters for graphics in Clj"
  :url ""
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [hello_lwjgl/lwjgl "2.9.1"]]
  :jvm-opts [~(str "-Djava.library.path=lwjgl-native:"
                   (System/getProperty "java.library.path"))])
