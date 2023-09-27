(defproject construction-papi "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://github.com/filipencopav/job-form-bot"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clj-commons/pomegranate "1.2.23"]
                 [clj-http "3.12.3"]
                 [cheshire "5.12.0"]
                 [telegrambot-lib "2.9.0"]
                 [com.taoensso/timbre "6.2.2"]]
  :main ^:skip-aot papi.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}
             :profiles/dev {}
             :profiles/test {}
             :project/dev {:env {:log-level "debug"}
                           :plugins [[lein-environ "1.1.0"]]}
             :project/test {:env {:log-level "info"}
                            :plugins [[lein-environ "1.1.0"]]}})
