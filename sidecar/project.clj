(defproject figwheel-sidecar "0.5.21-SNAPSHOT"
  :description "ClojureScript Autobuilder/Server which pushes changed files to the browser."
  :url "https://github.com/bhauman/lein-figwheel"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/bhauman/lein-figwheel"
        :dir ".."}
  :dependencies
  [[org.clojure/clojure "1.8.0"]
   [org.clojure/clojurescript "1.10.238"
    :exclusions [org.apache.ant/ant]]
   [org.clojure/core.async "0.4.474"
    :exclusions [org.clojure/tools.reader]]
   [com.stuartsierra/component "1.0.0"]
   [suspendable "0.1.1"
    :exclusions [org.clojure/clojure com.stuartsierra/component]]
   [http-kit "2.3.0"] ;; tested and works with 2.4.0-alpha-6
   [ring-cors "0.1.13"
    :exclusions [ring/ring-core org.clojure/clojure]]
   [ring/ring-core "1.6.3" ;; tested and works with 1.8.3
    :exclusions
    [org.clojure/tools.reader
     org.clojure/clojure]]
   [co.deps/ring-etag-middleware "0.2.1"]
   [clj-stacktrace "0.2.8"]
   [figwheel "0.5.21-SNAPSHOT"
      :exclusions [org.clojure/tools.reader]]
   [hawk "0.2.11" :exclusions [org.clojure/clojure]]

   [nrepl "0.5.3"]
   ;; for config validation
   [simple-lein-profile-merge "0.1.4"]
   [strictly-specking-standalone "0.1.1"]]

  :clean-targets ^{:protect false} ["dev-resources/public/js" "target"]

  :jvm-opts ^:replace ["-Xms256m" "-Xmx2g"]

  :profiles {:dev {:dependencies [[cider/piggieback "0.4.0"]]
                   :source-paths ["cljs_src" "src" "dev"]
                   :plugins [[lein-cljsbuild "1.1.3" :exclusions [[org.clojure/clojure]]]]}
             :repl {:source-paths ["cljs_src" "src"]
                    :resource-paths ["resources" "dev-resources"]
                    :repl-options {:init-ns figwheel-sidecar.repl-api}}}

  :cljsbuild {
             :builds
              [{:id "dev"
                :source-paths ["cljs_src" "../support/src"]
                :compiler {:main figwheel-helper.core
                           :asset-path "js/out"
                           :output-to  "dev-resources/public/js/figwheel-helper.js"
                           :output-dir "dev-resources/public/js/out"}
                }
               {:id "deploy"
                :source-paths ["cljs_src"]
                :compiler {:main figwheel-helper.core
                           :asset-path "js/out"
                           :output-to  "dev-resources/public/js/figwheel-helper-deploy.js"
                           :output-dir "target/deploy/out"
                           :optimizations :simple}
                }
               {:id "deploy-prod"
                :source-paths ["cljs_src"]
                :compiler {:main figwheel-helper.core
                           :asset-path "js/out"
                           :output-to  "resources/compiled-utils/figwheel-helper-deploy.js"
                           :output-dir "target/deploy-prod/out"
                           :optimizations :simple}
               }]
              })
