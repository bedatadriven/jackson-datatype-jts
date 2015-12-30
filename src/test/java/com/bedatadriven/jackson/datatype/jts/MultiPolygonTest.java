package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.node.BaseJsonNode;
import com.vividsolutions.jts.geom.*;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class MultiPolygonTest extends BaseJtsModuleTest<MultiPolygon> {
    @Override
    protected Class<MultiPolygon> getType() {
        return MultiPolygon.class;
    }

    @Override
    protected String createGeometryAsGeoJson() {
        return "{\"type\":\"MultiPolygon\",\"coordinates\":[[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]]]]}";
    }

    @Override
    protected MultiPolygon createGeometry() {
        LinearRing shell = gf.createLinearRing(new Coordinate[] {
                new Coordinate(102.0, 2.0), new Coordinate(103.0, 2.0),
                new Coordinate(103.0, 3.0), new Coordinate(102.0, 3.0),
                new Coordinate(102.0, 2.0) });
        return gf.createMultiPolygon(new Polygon[] { gf
                .createPolygon(shell, null) });
    }
}
