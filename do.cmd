@echo off

if [%1] == [help] (
  echo  w $V:   set gradle wrapper
  echo  fixgit: fix permission flag on git index for required files
  echo  b     : build
  echo  t     : test
  echo  pub   : publish plugin to https://plugins.gradle.org/plugin/biz.lermitage.oga
)

if [%1] == [w] (
  gradle wrapper --gradle-version=%2 --distribution-type all --no-daemon
)
if [%1] == [fixgit] (
  echo git update-index --chmod=+x gradlew
  git update-index --chmod=+x gradlew
)
if [%1] == [b] (
  gradlew clean build --warning-mode all --info --parallel --scan
)
if [%1] == [t] (
  gradlew build testClasses check --warning-mode all --info --parallel --scan
)
if [%1] == [pub] (
  gradlew clean build testClasses check publishPlugins --warning-mode all --info --parallel --scan
)
