package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class MultiPolygonParser extends BaseParser implements GeometryParser<MultiPolygon> {

    private PolygonParser helperParser;
    public MultiPolygonParser(GeometryFactory geometryFactory) {
        super(geometryFactory);
        helperParser = new PolygonParser(geometryFactory);
    }

    public MultiPolygon multiPolygonFromJson(JsonNode root) {
        JsonNode arrayOfPolygons = root.get(COORDINATES);
        MultiPolygon multiPolygon = geometryFactory.createMultiPolygon(polygonsFromJson(arrayOfPolygons));
        setGeometryFieldsFromJson(root, multiPolygon);
        return multiPolygon;
    }

    private Polygon[] polygonsFromJson(JsonNode arrayOfPolygons) {
        Polygon[] polygons = new Polygon[arrayOfPolygons.size()];
        for (int i = 0; i != arrayOfPolygons.size(); ++i) {
            polygons[i] = helperParser.polygonFromJsonArrayOfRings(arrayOfPolygons.get(i));
        }
        return polygons;
    }

    @Override
    public MultiPolygon geometryFromJson(JsonNode node) throws JsonMappingException {
        return multiPolygonFromJson(node);
    }
}
