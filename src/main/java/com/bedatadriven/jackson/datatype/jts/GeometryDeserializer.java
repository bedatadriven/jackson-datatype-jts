package com.bedatadriven.jackson.datatype.jts;

import com.bedatadriven.jackson.datatype.jts.parsers.GeometriesParser;
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


	private GeometriesParser parser = new GeometriesParser();

	@Override
	public Geometry deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException {
		ObjectCodec oc = jp.getCodec();
		JsonNode root = oc.readTree(jp);
		return parser.geometryFromJson(root);
	}

}
