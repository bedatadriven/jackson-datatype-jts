package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.locationtech.jts.geom.*;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class JtsModuleTest {
	private GeometryFactory gf = new GeometryFactory();
	private ObjectMapper mapper;
	@Before
	public void setupMapper() {

		mapper = new ObjectMapper();
		mapper.registerModule(new JtsModule());
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

}
