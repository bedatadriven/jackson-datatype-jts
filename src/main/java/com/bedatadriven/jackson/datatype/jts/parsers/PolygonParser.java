package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.*;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class PolygonParser extends PointParser{

    protected static GeometryFactory geometryFactory = new GeometryFactory();

    public static Polygon polygonFromJson(JsonNode node) {
        JsonNode arrayOfRings = node.get(COORDINATES);
        LinearRing shell = linearRingsFromJson(arrayOfRings.get(0));
        int size = arrayOfRings.size();
        LinearRing[] holes = new LinearRing[size - 1];
        for (int i = 1; i < size; i++) {
            holes[i] = linearRingsFromJson(arrayOfRings.get(i));
        }
        return geometryFactory.createPolygon(shell, holes);
    }

    private static LinearRing linearRingsFromJson(JsonNode coordinates) {
        assert coordinates.isArray() : "expected coordinates array";
        return geometryFactory.createLinearRing(PointParser.coordinatesFromJson(coordinates));
    }


    @Override
    public Geometry geometryFromJson(JsonNode node) throws JsonMappingException {
        return polygonFromJson(node);
    }
}
