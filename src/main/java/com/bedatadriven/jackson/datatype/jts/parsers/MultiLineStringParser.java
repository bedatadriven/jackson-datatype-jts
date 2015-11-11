package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class MultiLineStringParser extends LineStringParser{

    public static MultiLineString multiLineStringFromJson(JsonNode root) {
        return geometryFactory.createMultiLineString(
                lineStringsFromJson(root.get(COORDINATES)));
    }

    private static LineString[] lineStringsFromJson(JsonNode array) {
        LineString[] strings = new LineString[array.size()];
        for (int i = 0; i != array.size(); ++i) {
            strings[i] = geometryFactory.createLineString(coordinatesFromJson(array.get(i)));
        }
        return strings;
    }

    @Override
    public Geometry geometryFromJson(JsonNode node) throws JsonMappingException {
        return multiLineStringFromJson(node);
    }
}
