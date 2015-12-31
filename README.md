
# Jackson-datatype-jts

Jackson Module which provides custom serializers and deserializers for
[JTS Geometry](http://www.vividsolutions.com/jts/javadoc/com/vividsolutions/jts/geom/Geometry.html) objects
using the [GeoJSON format](http://www.geojson.org/geojson-spec.html)

[![Build Status](https://jenkins.bedatadriven.com/job/jackson-datatype-jts/badge/icon)](https://jenkins.bedatadriven.com/job/jackson-datatype-jts/)

## Installation 

Releases of jackson-datatype-jts are available on Maven Central.

### Maven

To use the module in Maven-based projects, use following dependency:

```xml
<dependency>
  <groupId>com.bedatadriven</groupId>
  <artifactId>jackson-datatype-jts</artifactId>
  <version>2.2</version>
</dependency>    
```

### Gradle


```gradle
dependencies {
    compile 'com.bedatadriven:jackson-datatype-jts:2.2'
}
```

## Usage

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
