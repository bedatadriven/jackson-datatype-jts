package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPoint;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class MultiPointParser extends PointParser {

    public static MultiPoint multiPointFromJson(JsonNode root) {
        return geometryFactory.createMultiPoint(
                coordinatesFromJson(root.get(COORDINATES)));
    }

    @Override
    public Geometry geometryFromJson(JsonNode node) throws JsonMappingException {
        return multiPointFromJson(node);
    }
}
