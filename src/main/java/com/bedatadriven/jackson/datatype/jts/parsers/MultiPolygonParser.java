package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class MultiPolygonParser extends PolygonParser {

    public static MultiPolygon multiPolygonFromJson(JsonNode root) {
        JsonNode arrayOfPolygons = root.get(COORDINATES);
        return PointParser.geometryFactory.createMultiPolygon(polygonsFromJson(arrayOfPolygons));
    }

    private static Polygon[] polygonsFromJson(JsonNode arrayOfPolygons) {
        Polygon[] polygons = new Polygon[arrayOfPolygons.size()];
        for (int i = 0; i != arrayOfPolygons.size(); ++i) {
            polygons[i] = polygonFromJson(arrayOfPolygons.get(i));
        }
        return polygons;
    }

    @Override
    public Geometry geometryFromJson(JsonNode node) throws JsonMappingException {
        return multiPolygonFromJson(node);
    }
}
