# JSpare Container

## Status

[![Build Status](https://travis-ci.org/jspare-projects/jspare-container.svg?branch=master)](https://travis-ci.org/jspare-projects/jspare-container)
[![Javadocs](http://www.javadoc.io/badge/org.jspare/jspare-core.svg)](http://www.javadoc.io/doc/org.jspare/jspare-core)

## Description

The JSpare Container provide an core environment container for your java applications. Prepare applications uncoupled and componentized. Using JSpare Container your application will contain several features that can help to define your application, such as:

* Easy Bootstrap for your Java application
* Simple environment configuration
* Forget all annoying application mapping, like the legacy of xml
* Modern and intuitive Java Container
* Inversion control with direct injection in your class
* Easy way to create components and resources, uncoupling your architecture
* Qualification and a practical way to change implementation of a component
* Commons Configuration based on "Apache Commons Configuration"
* Simple conventions, for improve your experience

## Installation and Getting Started

The reference documentation includes detailed installation instructions as well as a comprehensive getting started guide.

Here is a quick sample of a simple usage of JSpare Container

For maven:

```xml
<parent>
	<groupId>org.jspare</groupId>
	<artifactId>jspare-core</artifactId>
	<version>2.1.0</version>
</parent>
```

For gradle:

```
sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compile "org.jspare:jspare-core:2.1.0"
}
```


## Getting help and Reporting Issues

Having trouble with JSpare Container? We’d like to help!

* Check the reference documentation at http://jspare.org/jspare-container — they provide solutions to the most common questions.
* Report bugs and issues at https://github.com/jspare-projects/jspare-container/issues

## Other Projects

Be sure to visit our other projects, jspare-container is the basis of all our frameworks and solutions. See it at: http://jspare.org/ or here on github: https://github.com/jspare-projects/

## License

All JSpare projects are Open Source software released under the Apache 2.0 license.
