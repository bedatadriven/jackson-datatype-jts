package com.bedatadriven.jackson.datatype.jts;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public class GeometryCollectionParserTest extends BaseJtsModuleTest<GeometryCollection> {
    @Override
    protected Class<GeometryCollection> getType() {
        return GeometryCollection.class;
    }

    @Override
    protected String createGeometryAsGeoJson() {
        return "{\"type\":\"GeometryCollection\",\"geometries\":[{\"type\":\"Point\",\"coordinates\":[1.2345678,2.3456789]}]}";
    }

    @Override
    protected GeometryCollection createGeometry() {
        return gf.createGeometryCollection(new Geometry[] {
                gf.createPoint(new Coordinate(1.2345678, 2.3456789)) });
    }

}
