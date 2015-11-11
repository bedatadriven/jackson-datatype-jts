package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import java.io.IOException;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.COORDINATES;
import static com.bedatadriven.jackson.datatype.jts.GeoJson.TYPE;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class PointDeserializer extends JsonDeserializer<Point> {
    private GeometryFactory gf = new GeometryFactory();
    @Override
    public Point deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode root = oc.readTree(jsonParser);
        return parsePoint(root);
    }

    private Point parsePoint(JsonNode node) {
        return gf.createPoint(
                parseCoordinate(node.get(COORDINATES)));
    }

    private Coordinate parseCoordinate(JsonNode array) {
        assert array.isArray() && array.size() == 2 : "expecting coordinate array with single point [ x, y ]";
        return new Coordinate(
                array.get(0).asDouble(),
                array.get(1).asDouble());
    }

    private Coordinate[] parseCoordinates(JsonNode array) {
        Coordinate[] points = new Coordinate[array.size()];
        for (int i = 0; i != array.size(); ++i) {
            points[i] = parseCoordinate(array.get(i));
        }
        return points;
    }

}
