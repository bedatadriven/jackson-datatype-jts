package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.*;

import java.util.HashMap;
import java.util.Map;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.*;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class GeometriesParser extends MultiPointParser{

    private Map<String, GeometryParser> parsers;

    public GeometriesParser() {
        parsers = new HashMap<String, GeometryParser>();
        parsers.put(POINT, new PointParser());
        parsers.put(MULTI_POINT, new MultiPointParser());
        parsers.put(LINE_STRING, new LineStringParser());
        parsers.put(MULTI_LINE_STRING, new MultiLineStringParser());
        parsers.put(POLYGON, new PolygonParser());
        parsers.put(MULTI_POLYGON, new MultiPolygonParser());
        parsers.put(GEOMETRIES, this);
    }

    private GeometryCollection geometryCollectionFromJson(JsonNode root) throws JsonMappingException {
        return geometryFactory.createGeometryCollection(
                geometriesFromJson(root.get(GEOMETRIES)));
    }

    private Geometry[] geometriesFromJson(JsonNode arrayOfGeoms) throws JsonMappingException {
        Geometry[] items = new Geometry[arrayOfGeoms.size()];
        for(int i=0;i!=arrayOfGeoms.size();++i) {
            items[i] = geometryFromJson(arrayOfGeoms.get(i));
        }
        return items;
    }

    @Override
    public Geometry geometryFromJson(JsonNode node) throws JsonMappingException {
        String typeName = node.get(TYPE).asText();
        GeometryParser parser = parsers.get(typeName);
        if (parser != null) {
            return parser.geometryFromJson(node);
        }
        else {
            throw new JsonMappingException("Invalid geometry type: " + typeName);
        }
    }
}
