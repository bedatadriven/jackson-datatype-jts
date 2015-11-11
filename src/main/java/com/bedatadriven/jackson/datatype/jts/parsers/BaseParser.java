package com.bedatadriven.jackson.datatype.jts.parsers;

import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class BaseParser {

    protected GeometryFactory geometryFactory;

    public BaseParser(GeometryFactory geometryFactory) {
        this.geometryFactory = geometryFactory;
    }

}
