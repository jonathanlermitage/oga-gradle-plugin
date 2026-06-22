## Old GroupIds Alerter - Change Log

### 2.0.0 (2026/06/22)
* migrate to Gradle 8 and Java 21. Test with Gradle 8 and 9.
* make the plugin compatible with Gradle's configuration cache, which may be enabled by default in Gradle 10.
* minor code reworks.
* a future update may provide feature parity with the original Maven plugin.

### 1.1.1 (2021/12/07)
* build the plugin with Gradle 7.3.1. It should be compatible with Gradle from 4.10 to 7+.

### 1.1.0 (2019/07/21)
* internal : use gradle logger.
* fail the build if found deprecated dependencies.

### 1.0.0 (2019/07/19)
* the main task `biz-lermitage-oga-gradle-check` works.
