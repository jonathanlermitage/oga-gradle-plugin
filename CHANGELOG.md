## Old GroupIds Alerter - Change Log

### WIP
* migrate code to Kotlin then reuse code from the OGA Maven plugin (because it's already written in Kotlin)
* duplicate most of the OGA Maven plugin features

### 1.1.1 (2021/12/07)
* build the plugin with Gradle 7.3.1. It should be compatible with Gradle from 4.10 to 7+.

### 1.1.0 (2019/07/21)
* internal : use gradle logger.
* fail the build if found deprecated dependencies.

### 1.0.0 (2019/07/19)
* the main task `biz-lermitage-oga-gradle-check` works.
