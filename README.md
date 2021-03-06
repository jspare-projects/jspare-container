# JSpare Container

## Status

[![Build Status](https://travis-ci.org/jspare-projects/jspare-container.svg?branch=master)](https://travis-ci.org/jspare-projects/jspare-container)
[![Javadocs](http://www.javadoc.io/badge/org.jspare/jspare-core.svg)](http://www.javadoc.io/doc/org.jspare/jspare-core)

## Description

The Jspare Container provide an core environment container for your java applications. Prepare applications uncoupled and componentized. Using Jspare Container your application will contain several features that can help to define your application, such as:

* Lightweight IoC and DI;
* JSR-330 and JSR-250;
* Minimalist api to mock unit tests with DI.
* Forget all annoying application mapping, like the legacy of xml;
* Easy Bootstrap for your Java application;
* Components based;
* Bind strategy;
* Simple conventions, for improve your experience.
* Easy way to create components and resources, uncoupling your architecture;

## Installation and Getting Started

The reference documentation includes detailed installation instructions as well as a comprehensive getting started guide.

Here is a quick sample of a simple usage of Jspare Container

For maven:

```xml
<parent>
  <groupId>org.jspare</groupId>
  <artifactId>jspare-core</artifactId>
  <version>${jspare.version}</version>
</parent>
```

For gradle:

```
sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compile "org.jspare:jspare-core:${jspare.version}"
}
```

## Documentation

You can [find the jspare-container documentation here](https://github.com/jspare-projects/jspare-container/wiki) which has extended usage instructions and other useful information. Substantial usage information can be found in the API documentation.


## Getting help and Reporting Issues

Having trouble with JSpare Container? We’d like to help!

* Check the reference documentation at Wiki — they provide solutions to the most common questions.
* Report bugs and issues at https://github.com/jspare-projects/jspare-container/issues

## Other Projects

Be sure to visit our other projects, jspare-container is the basis of all our frameworks and solutions. See it at: http://jspare.org/ or here on github: https://github.com/jspare-projects/

## License

All JSpare projects are Open Source software released under the Apache 2.0 license.
