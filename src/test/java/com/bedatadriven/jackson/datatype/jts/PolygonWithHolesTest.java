package com.bedatadriven.jackson.datatype.jts;


import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LinearRing;
import org.locationtech.jts.geom.Polygon;

import static org.junit.Assert.assertThat;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class PolygonWithHolesTest extends BaseJtsModuleTest<Polygon> {
    @Override
    protected Class<Polygon> getType() {
        return Polygon.class;
    }

    @Override
    protected String createGeometryAsGeoJson() {
        return "{\"type\":\"Polygon\",\"coordinates\":[[[102.0,2.0],[103.0,2.0],[103.0,3.0],[102.0,3.0],[102.0,2.0]],[[100.2,0.2],[100.8,0.2],[100.8,0.8],[100.2,0.8],[100.2,0.2]]]}";
    }

    @Override
    protected Polygon createGeometry() {
        LinearRing shell = gf.createLinearRing(new Coordinate[] {
                new Coordinate(102.0, 2.0), new Coordinate(103.0, 2.0),
                new Coordinate(103.0, 3.0), new Coordinate(102.0, 3.0),
                new Coordinate(102.0, 2.0) });
        LinearRing[] holes = new LinearRing[] { gf
                .createLinearRing(new Coordinate[] {
                new Coordinate(100.2, 0.2), new Coordinate(100.8, 0.2),
                new Coordinate(100.8, 0.8), new Coordinate(100.2, 0.8),
                new Coordinate(100.2, 0.2) }) };
        return gf.createPolygon(shell, holes);
    }
}