<h1 align="center">
    Old GroupIds Alerter - Gradle Plugin
</h1>

<p align="center">
    <a href="https://travis-ci.org/jonathanlermitage/oga-gradle-plugin"><img src="https://travis-ci.org/jonathanlermitage/oga-gradle-plugin.svg?branch=master"/></a>
    <a href="https://github.com/jonathanlermitage/oga-gradle-plugin/blob/master/LICENSE.txt"><img src="https://img.shields.io/github/license/jonathanlermitage/oga-gradle-plugin.svg"/></a>
</p>

*Looking for a Maven plugin? Check [oga-maven-plugin](https://github.com/jonathanlermitage/oga-maven-plugin).*

A Gradle plugin that checks for deprecated *groupId + artifactId* couples, in order to reduce usage of non-maintained 3rd-party code (e.g. did you know that artifact `graphql-spring-boot-starter` moved from `from com.graphql-java` to `com.graphql-java-kickstart`?).

Tested with Gradle 4.10.3 and 5.5.1 on JDK8 and JDK11.

## Author

Jonathan Lermitage (<jonathan.lermitage@gmail.com>)  
Linkedin profile: [jonathan-lermitage-092711142](https://www.linkedin.com/in/jonathan-lermitage-092711142/)

## Usage

Using the plugins DSL:

```groovy
plugins {
  id "biz.lermitage.oga" version "1.0.0"
}
```

Otherwise, using legacy plugin application:

```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.biz.lermitage.oga:oga-gradle-plugin:1.0.0"
  }
}

apply plugin: "biz.lermitage.oga"
```

Then launch `./gradlew biz-lermitage-oga-gradle-check`. If any deprecated *groupId + artifactId* couple if found, error message(s) will be displayed, but the build will won't fail (a future release will bring real error messages and fail the build).

See [plugins.gradle.org/plugin/biz.lermitage.oga](https://plugins.gradle.org/plugin/biz.lermitage.oga) for details.

## Build

`./gradlew build --scan`

## Contribution

### Code 

Open an issue or a pull-request. Contributions must be tested at least on JDK8.  
Please reformat new code only: do not reformat the whole project or entire existing file (in other words, try do limit the amount of changes in order to speed up code review).

### Definitions file

See [oga-maven-plugin#definitions-file](https://github.com/jonathanlermitage/oga-maven-plugin#definitions-file). The same definitions file is used for both Maven and Gradle plugins.

### Find new entries for definitions file

See [oga-maven-plugin#find-new-entries-for-definitions-file](https://github.com/jonathanlermitage/oga-maven-plugin#find-new-entries-for-definitions-file).

## License

MIT License. In other words, you can do what you want: this project is entirely OpenSource, Free and Gratis.
