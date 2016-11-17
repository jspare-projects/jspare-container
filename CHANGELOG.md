# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) 
and this project adheres to [Semantic Versioning](http://semver.org/).

## [Unreleased]

## [1.1.0] - 2016-11-17
### Added
- Change Log file.

### Removed
- Removed package 'org.jspare.core.serializer' and all your features. We understand that it is not the responsibility of the framework the serialization components available in the previous version, and it is up to each application to choose the best provider for this purpose.
- Removed package 'org.jspare.core.collections' and all your featyres. We have moved to repo jspare-commons/jspare-collections

## 1.0.0 - 2016-09-10
### Added
- Application container
- Easy way to create components, uncoupling your architecture
- Inversion control with injection on your class
- Qualification for inversion control
- Configuration based on external config file
- Native resource loader for classpath and external resources
- Native serializer to json, base64 and byte[]
- New collections like: TreeNode, TimedMap and MultiValueMap
