package com.bedatadriven.jackson.datatype.jts.parsers;

import static com.bedatadriven.jackson.datatype.jts.GeoJson.SRID;

import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class BaseParser {

    protected GeometryFactory geometryFactory;

    public BaseParser(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
    }

    /**
     * Sets all fields that belongs to Geometry class from a JsonNode
     * @param node the JsonNode being parsed
     * @param geometry the Geometry object being parsed
     */
    protected void setGeometryFieldsFromJson(JsonNode node, Geometry geometry) {

      if (node.has(SRID)) {
        geometry.setSRID(node.get(SRID).asInt());
      }
    }

}
