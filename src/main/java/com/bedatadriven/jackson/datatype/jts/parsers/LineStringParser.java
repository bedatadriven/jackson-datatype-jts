package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class LineStringParser extends MultiPointParser {

    public static LineString lineStringFromJson(JsonNode root) {
        return geometryFactory.createLineString(
                coordinatesFromJson(root.get(COORDINATES)));
    }

    @Override
    public Geometry geometryFromJson(JsonNode node) throws JsonMappingException {
        return lineStringFromJson(node);
    }
}
