package com.bedatadriven.jackson.datatype.jts.parsers;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.vividsolutions.jts.geom.Geometry;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public interface GeometryParser<T extends Geometry> {

    T geometryFromJson(JsonNode node) throws JsonMappingException;

}
