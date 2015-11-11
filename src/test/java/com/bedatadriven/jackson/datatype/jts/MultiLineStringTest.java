package com.bedatadriven.jackson.datatype.jts;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class MultiLineStringTest extends BaseJtsModuleTest<MultiLineString> {
    @Override
    protected Class<MultiLineString> getType() {
        return MultiLineString.class;
    }

    @Override
    protected String createGeometryAsGeoJson() {
        return "{\"type\":\"MultiLineString\",\"coordinates\":[[[100.0,0.0],[101.0,1.0]],[[102.0,2.0],[103.0,3.0]]]}";
    }

    @Override
    protected MultiLineString createGeometry() {
        return gf
                .createMultiLineString(new LineString[] {
                        gf.createLineString(new Coordinate[] {
                                new Coordinate(100.0, 0.0),
                                new Coordinate(101.0, 1.0) }),
                        gf.createLineString(new Coordinate[] {
                                new Coordinate(102.0, 2.0),
                                new Coordinate(103.0, 3.0) }) });
    }
}
