package com.bedatadriven.jackson.datatype.jts;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.Point;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class MultiPointTest extends BaseJtsModuleTest<MultiPoint> {
    @Override
    protected Class<MultiPoint> getType() {
        return MultiPoint.class;
    }

    @Override
    protected String createGeometryAsGeoJson() {
        return "{\"type\":\"MultiPoint\",\"coordinates\":[[1.2345678,2.3456789]]}";
    }

    @Override
    protected MultiPoint createGeometry() {
        return gf.createMultiPoint(new Point[] {gf
                .createPoint(new Coordinate(1.2345678, 2.3456789))});
    }

    @Override
    protected String createGeometryAsGeoJsonWithSrid() {
        return "{\"type\":\"MultiPoint\",\"coordinates\":[[1.2345678,2.3456789]],\"srid\":2154}";
    }

    @Override
    protected MultiPoint createGeometryWithSrid() {
      MultiPoint geom = gf.createMultiPoint(new Point[] {gf
      .createPoint(new Coordinate(1.2345678, 2.3456789))});
      geom.setSRID(2154);
      return geom;
    }
}
