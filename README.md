
Jackson Module which provides custom serializers and deserializers for
[JTS Geometry](http://www.vividsolutions.com/jts/javadoc/com/vividsolutions/jts/geom/Geometry.html) objects
using the [GeoJSON format](http://www.geojson.org/geojson-spec.html)

[![Build Status](https://jenkins.bedatadriven.com/job/jackson-datatype-jts/badge/icon)](https://jenkins.bedatadriven.com/job/jackson-datatype-jts/)

### Maven Dependency

To use module on Maven-based projects, use following dependency:

```xml
<dependency>
  <groupId>com.bedatadriven</groupId>
  <artifactId>jackson-datatype-jts</artifactId>
  <version>2.0</version>
</dependency>    
```

### Gradle dependency


```gradle
 repositories {
        // ...
        maven { url "https://jitpack.io" }
    }
    
  ...
  dependencies {
	    compile 'com.github.purpleP:jackson-datatype-jts:jackson-datatype-jts-1.1.1'
	}
```

(or whatever version is most up-to-date at the moment)


### Registering module

To use JTS geometry datatypes with Jackson, you will first need to register the module first (same as
with all Jackson datatype modules):

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JtsModule());
```

### Reading and Writing Geometry types

After registering JTS module, [Jackson Databind](https://github.com/FasterXML/jackson-databind)
will be able to write Geometry instances as GeoJSON and
and read GeoJSON geometries as JTS Geometry objects.

To write a Point object as GeoJSON:

```java
GeometryFactory gf = new GeometryFactory();
Point point = gf.createPoint(new Coordinate(1.2345678, 2.3456789));
String geojson = objectMapper.writeValueAsString(point);
```

You can also read GeoJSON in as JTS geometry objects:

```java
InputStream in;
Point point = mapper.readValue(in, Point.class);
```
