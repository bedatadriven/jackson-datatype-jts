package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vividsolutions.jts.geom.*;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JtsModuleTest {
	private GeometryFactory gf = new GeometryFactory();
	private ObjectWriter writer;
	private ObjectMapper mapper;

	@Before
	public void setupMapper() {

		mapper = new ObjectMapper();
		mapper.registerModule(new JtsModule());

		writer = mapper.writer();
	}

	@Test
	public void point() throws Exception {
		Point point = gf.createPoint(new Coordinate(1.2345678, 2.3456789));
		
		assertRoundTrip(point);
		assertThat(
				toJson(point),
				equalTo("{\"type\":\"Point\",\"coordinates\":[1.2345678,2.3456789]}"));
	}

	private String toJson(Object value) throws IOException {
		return writer.writeValueAsString(value);
	}

	@Test
	public void multiPoint() throws Exception {
		MultiPoint multiPoint = gf.createMultiPoint(new Point[] { gf
				.createPoint(new Coordinate(1.2345678, 2.3456789)) });
		assertRoundTrip(multiPoint);
		assertThat(
				toJson(multiPoint),
				equalTo("{\"type\":\"MultiPoint\",\"coordinates\":[[1.2345678,2.3456789]]}"));
	}

	@Test
	public void lineString() throws Exception {
		LineString lineString = gf.createLineString(new Coordinate[] {
				new Coordinate(100.0, 0.0), new Coordinate(101.0, 1.0) });
		assertRoundTrip(lineString);
		assertThat(
				toJson(lineString),
				equalTo("{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}"));
	}

	@Test
	public void multiLineString() throws Exception {

		MultiLineString multiLineString = gf
				.createMultiLineString(new LineString[] {
						gf.createLineString(new Coordinate[] {
								new Coordinate(100.0, 0.0),
								new Coordinate(101.0, 1.0) }),
						gf.createLineString(new Coordinate[] {
								new Coordinate(102.0, 2.0),
								new Coordinate(103.0, 3.0) }) });

		assertRoundTrip(multiLineString);
		assertThat(
				toJson(multiLineString),
				equalTo("{\"type\":\"MultiLineString\",\"coordinates\":[[[100.0,0.0],[101.0,1.0]],[[102.0,2.0],[103.0,3.0]]]}"));
	}

	@Test
	public void polygon() throws Exception {
		LinearRing shell = gf.createLinearRing(new Coordinate[] {
				new Coordinate(102.0, 2.0), new Coordinate(103.0, 2.0),
				new Coordinate(103.0, 3.0), new Coordinate(102.0, 3.0),
				new Coordinate(102.0, 2.0) });
		LinearRing[] holes = new LinearRing[0];
		Polygon polygon = gf.createPolygon(shell, holes);

		assertRoundTrip(polygon);
		assertThat(
				toJson(polygon),
				equalTo("{\"type\":\"Polygon\",\"coordinates\":[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]]}"));
	}

	@Test(expected = JsonMappingException.class)
	public void invalidGeometryType() throws IOException {
		String json = "{\"type\":\"Singularity\",\"coordinates\":[]}";
		mapper.readValue(json, Geometry.class);
	}
	
	@Test(expected = JsonMappingException.class)
	public void unsupportedGeometry() throws IOException {
		Geometry unsupportedGeometry = EasyMock.createNiceMock("NonEuclideanGeometry", Geometry.class);
		EasyMock.replay(unsupportedGeometry);
		
		mapper.writeValue(System.out, unsupportedGeometry);
	}


	@Test
	public void polygonWithHoles() throws Exception {
		LinearRing shell = gf.createLinearRing(new Coordinate[] {
				new Coordinate(102.0, 2.0), new Coordinate(103.0, 2.0),
				new Coordinate(103.0, 3.0), new Coordinate(102.0, 3.0),
				new Coordinate(102.0, 2.0) });
		LinearRing[] holes = new LinearRing[] { gf
				.createLinearRing(new Coordinate[] {
						new Coordinate(100.2, 0.2), new Coordinate(100.8, 0.2),
						new Coordinate(100.8, 0.8), new Coordinate(100.2, 0.8),
						new Coordinate(100.2, 0.2) }) };
		assertThat(
        toJson(gf.createPolygon(shell, holes)),
        equalTo("{\"type\":\"Polygon\",\"coordinates\":[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]],[[100.2,0.2],[100.8,0.2],[100.8,0.8],[100.2,0.8],[100.2,0.2]]]}"));
	}

	@Test
	public void multiPolygon() throws Exception {
		LinearRing shell = gf.createLinearRing(new Coordinate[] {
				new Coordinate(102.0, 2.0), new Coordinate(103.0, 2.0),
				new Coordinate(103.0, 3.0), new Coordinate(102.0, 3.0),
				new Coordinate(102.0, 2.0) });
		MultiPolygon multiPolygon = gf.createMultiPolygon(new Polygon[] { gf
				.createPolygon(shell, null) });

		assertRoundTrip(multiPolygon);
		assertThat(
				toJson(multiPolygon),
				equalTo("{\"type\":\"MultiPolygon\",\"coordinates\":[[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]]]}"));
	}

	@Test
	public void geometryCollection() throws Exception {
		GeometryCollection collection = gf
				.createGeometryCollection(new Geometry[] { gf
						.createPoint(new Coordinate(1.2345678, 2.3456789)) });
		assertRoundTrip(collection);
		assertThat(
				toJson(collection),
				equalTo("{\"type\":\"GeometryCollection\",\"geometries\":[{\"type\":\"Point\",\"coordinates\":[1.2345678,2.3456789]}]}"));
	}
	
	private void assertRoundTrip(Geometry geom) throws IOException {
		String json = writer.writeValueAsString(geom);
		System.out.println(json);
		Geometry regeom = mapper.readValue(json, geom.getClass());
		assertThat(regeom, equalTo(geom));
	}
}
