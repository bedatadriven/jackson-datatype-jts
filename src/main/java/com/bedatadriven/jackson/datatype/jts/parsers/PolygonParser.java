package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.*;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class PolygonParser extends BaseParser implements GeometryParser<Polygon> {

    public PolygonParser(GeometryFactory geometryFactory) {
        super(geometryFactory);
    }

    public Polygon polygonFromJson(JsonNode node) {
        JsonNode arrayOfRings = node.get(COORDINATES);
        return polygonFromJsonArrayOfRings(arrayOfRings);
    }

    public Polygon polygonFromJsonArrayOfRings(JsonNode arrayOfRings) {
        LinearRing shell = linearRingsFromJson(arrayOfRings.get(0));
        int size = arrayOfRings.size();
        LinearRing[] holes = new LinearRing[size - 1];
        for (int i = 1; i < size; i++) {
            holes[i - 1] = linearRingsFromJson(arrayOfRings.get(i));
        }
        return geometryFactory.createPolygon(shell, holes);
    }

    private LinearRing linearRingsFromJson(JsonNode coordinates) {
        assert coordinates.isArray() : "expected coordinates array";
        return geometryFactory.createLinearRing(PointParser.coordinatesFromJson(coordinates));
    }


    @Override
    public Polygon geometryFromJson(JsonNode node) throws JsonMappingException {
        return polygonFromJson(node);
    }
}
