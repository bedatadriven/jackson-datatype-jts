package com.bedatadriven.jackson.datatype.jts;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author lainard on 28/06/16.
 */
public class D3DPointTest extends BaseJtsModuleTest<Point> {

    @Override
    protected Class<Point> getType() {
        return Point.class;
    }

    @Override
    protected String createGeometryAsGeoJson() {
        return "{\"type\":\"Point\",\"coordinates\":[1.2345678,2.3456789,200.0]}";
    }

    @Override
    protected Point createGeometry() {
        return gf.createPoint(new Coordinate(1.2345678, 2.3456789,200.0));
    }

}
