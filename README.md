# JSpare Container

## Status

[![Build Status](https://travis-ci.org/jspare-projects/jspare-container.svg?branch=master)](https://travis-ci.org/jspare-projects/jspare-container)

## Description

The JSpare Container provide an core environment container for your java 8 applications. Prepare applications uncoupled and componentized. Using JSpare Container your application will contain several features that can help to define your application, such as:

* Application container
* Easy way to create components, uncoupling your architecture 
* Inversion control with injection on your class
* Qualification for inversion control
* Configuration based on external config file
* Native resource loader for classpath and external resources
* Native serializer to json, base64 and byte[]
* New collections like: TreeNode, TimedMap and MultiValueMap

## Installation and Getting Started

The reference documentation includes detailed installation instructions as well as a comprehensive getting started guide.

Here is a quick sample of a simple usage of JSpare Container

For maven:

```xml
<parent>
	<groupId>org.jspare</groupId>
	<artifactId>jspare-core</artifactId>
	<version>1.0.0</version>
</parent>
```

For gradle:

```
sourceCompatibility = 1.8
targetCompatibility = 1.8

dependencies {
    compile "org.jspare:jspare-core:1.0.0"
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