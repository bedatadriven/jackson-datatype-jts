package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class PointParser implements GeometryParser{

    protected static GeometryFactory geometryFactory = new GeometryFactory();

    public static Coordinate coordinateFromJson(JsonNode array) {
        assert array.isArray() && array.size() == 2 : "expecting coordinate array with single point [ x, y ]";
        return new Coordinate(
                array.get(0).asDouble(),
                array.get(1).asDouble());
    }

    public static Coordinate[] coordinatesFromJson(JsonNode array) {
        Coordinate[] points = new Coordinate[array.size()];
        for (int i = 0; i != array.size(); ++i) {
            points[i] = PointParser.coordinateFromJson(array.get(i));
        }
        return points;
    }

    public Point pointFromJson(JsonNode node) {
        return geometryFactory.createPoint(
                coordinateFromJson(node.get(COORDINATES)));
    }

    @Override
    public Geometry geometryFromJson(JsonNode node) throws JsonMappingException {
        return pointFromJson(node);
    }
}
