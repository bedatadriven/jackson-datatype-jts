package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.*;

import java.io.IOException;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.*;

public class GeometryDeserializer extends JsonDeserializer<Geometry> {

	private GeometryFactory gf;

	public GeometryDeserializer() {
		this(new GeometryFactory());
	}

	public GeometryDeserializer(GeometryFactory gf) {
		this.gf = gf;
	}

	@Override
	public Geometry deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException {
		ObjectCodec oc = jp.getCodec();
		JsonNode root = oc.readTree(jp);
		return parseGeometry(root);
	}

	private Geometry parseGeometry(JsonNode root) throws JsonMappingException {
		String typeName = root.get(TYPE).asText();
		if (POINT.equals(typeName)) {
			return parsePoint(root);

		} else if(MULTI_POINT.equals(typeName)) {
			return parseMultiPoint(root);

		} else if(LINE_STRING.equals(typeName)) {
			return parseLineString(root);

		} else if (MULTI_LINE_STRING.equals(typeName)) {
			return parseMultiLineStrings(root);

		} else if(POLYGON.equals(typeName)) {
			return parsePolygon(root);

		} else if (MULTI_POLYGON.equals(typeName)) {
			return parseMultiPolygon(root);

		} else if (GEOMETRY_COLLECTION.equals(typeName)) {
			return parseGeometryCollection(root);

		} else {
			throw new JsonMappingException("Invalid geometry type: " + typeName);
		}
	}

	private GeometryCollection parseGeometryCollection(JsonNode root) throws JsonMappingException {
		return gf.createGeometryCollection(
				parseGeometries(root.get(GEOMETRIES)));
	}

	private MultiPolygon parseMultiPolygon(JsonNode root) {
		JsonNode arrayOfPolygons = root.get(COORDINATES);
		return gf.createMultiPolygon(parsePolygons(arrayOfPolygons));
	}

	private Polygon parsePolygon(JsonNode root) {
		JsonNode arrayOfRings = root.get(COORDINATES);
		return parsePolygonCoordinates(arrayOfRings);
	}

	private MultiLineString parseMultiLineStrings(JsonNode root) {
		return gf.createMultiLineString(
				parseLineStrings(root.get(COORDINATES)));
	}

	private LineString parseLineString(JsonNode root) {
		return gf.createLineString(
				parseCoordinates(root.get(COORDINATES)));
	}

	private Point parsePoint(JsonNode root) {
		return gf.createPoint(
				parseCoordinate(root.get(COORDINATES)));
	}

	private MultiPoint parseMultiPoint(JsonNode root) {
		return gf.createMultiPoint(
				parseCoordinates(root.get(COORDINATES)));
	}

	private Geometry[] parseGeometries(JsonNode arrayOfGeoms) throws JsonMappingException {
		Geometry[] items = new Geometry[arrayOfGeoms.size()];
		for(int i=0;i!=arrayOfGeoms.size();++i) {
			items[i] = parseGeometry(arrayOfGeoms.get(i));
		}
		return items;
	}

	private Polygon parsePolygonCoordinates(JsonNode arrayOfRings) {
		return gf.createPolygon(
				parseLinearRing(arrayOfRings.get(0)),
				parseInteriorRings(arrayOfRings));
	}

	private Polygon[] parsePolygons(JsonNode arrayOfPolygons) {
		Polygon[] polygons = new Polygon[arrayOfPolygons.size()];
		for (int i = 0; i != arrayOfPolygons.size(); ++i) {
			polygons[i] = parsePolygonCoordinates(arrayOfPolygons.get(i));
		}
		return polygons;
	}

	private LinearRing parseLinearRing(JsonNode coordinates) {
		assert coordinates.isArray() : "expected coordinates array";
		
		return gf.createLinearRing(parseCoordinates(coordinates));
	}

	private LinearRing[] parseInteriorRings(JsonNode arrayOfRings) {
		LinearRing[] rings = new LinearRing[arrayOfRings.size() - 1];
		for (int i = 1; i < arrayOfRings.size(); ++i) {
			rings[i - 1] = parseLinearRing(arrayOfRings.get(i));
		}
		return rings;
	}

	private Coordinate parseCoordinate(JsonNode array) {
		assert array.isArray() && array.size() == 2 : "expecting coordinate array with single point [ x, y ]";
		return new Coordinate(
				array.get(0).asDouble(),
				array.get(1).asDouble());
	}

	private Coordinate[] parseCoordinates(JsonNode array) {
		Coordinate[] points = new Coordinate[array.size()];
		for (int i = 0; i != array.size(); ++i) {
			points[i] = parseCoordinate(array.get(i));
		}
		return points;
	}

	private LineString[] parseLineStrings(JsonNode array) {
		LineString[] strings = new LineString[array.size()];
		for (int i = 0; i != array.size(); ++i) {
			strings[i] = gf.createLineString(parseCoordinates(array.get(i)));
		}
		return strings;
	}
}
