package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.databind.deser.Deserializers;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class LineStringTest extends BaseJtsModuleTest<LineString> {
    @Override
    protected Class<LineString> getType() {
        return LineString.class;
    }

    @Override
    protected String createGeometryAsGeoJson() {
        return "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]]}";
    }

    @Override
    protected LineString createGeometry() {
        return gf.createLineString(new Coordinate[] {
                new Coordinate(100.0, 0.0), new Coordinate(101.0, 1.0) });
    }

    @Override
    protected String createGeometryAsGeoJsonWithSrid() {
      return "{\"type\":\"LineString\",\"coordinates\":[[100.0,0.0],[101.0,1.0]],\"srid\":2154}";
    }

    @Override
    protected LineString createGeometryWithSrid() {
      LineString geom = gf.createLineString(new Coordinate[] {
              new Coordinate(100.0, 0.0), new Coordinate(101.0, 1.0) });
      geom.setSRID(2154);
      return geom;
    }

}