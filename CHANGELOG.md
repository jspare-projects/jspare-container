# Change Log
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/) 
and this project adheres to [Semantic Versioning](http://semver.org/).

## TODO List

- Improve Javadoc API
- Native support to OSGi
- Migrate to Gradle

## [3.0.0]

### Added
- Allow injection of any interface registered
- Follow JSR-330
- Follow JSR-250
- Improved unit tests
- @ImplementedBy to indicate interface implementation

### Updated
- Environment My now use Injection Strategy
- Improving Injector
- @Component allow package impl pattern
- @Component and @Resource indicate impl classes

### Removed
- Removed deprecated method Environment.registryInjector(Class<? extends Annotation> annClazz, InjectorStrategy injector)
- Removed deprecated method ContainerUtils.processInjection(Class<?> clazz, Object result)

## [2.1.0] - 25-02-2017

### Added

- Release artifcats to m2 release.
- Added native Application Context Resource
- Added Injector Strategy to customize inversion of control
- Added Resource injection with @Resource for non components

### Update

- Improved Environment Exception and standardized error codes
- Improved CommonsConfiguration with Apache Configurations
- Improved unit tests to more than 85% of coverage.
- Improved performance of Environment Container
- Restructured Environment Container

## [2.0.0] - 12-12-2016

## [1.1.0] - 17-11-2016

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
