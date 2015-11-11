package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public abstract class BaseJtsModuleTest<T extends Geometry> {
    protected GeometryFactory gf = new GeometryFactory();
    private ObjectWriter writer;
    private ObjectMapper mapper;
    private T geometry;
    private String geometryAsGeoJson;

    protected BaseJtsModuleTest() {
    }

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JtsModule());
        writer = mapper.writer();
        geometry = createGeometry();
        geometryAsGeoJson = createGeometryAsGeoJson();
    }

    protected abstract Class<T> getType();

    protected abstract String createGeometryAsGeoJson();

    protected abstract T createGeometry();


    @Test
    public void shouldDeserializeConcreteType() throws Exception {
        T concreteGeometry = mapper.readValue(geometryAsGeoJson, getType());
        assertThat(
                toJson(concreteGeometry),
                equalTo(geometryAsGeoJson));
    }

    @Test
    public void shouldDeserializeAsInterface() throws Exception {
        assertRoundTrip(geometry);
        assertThat(
                toJson(geometry),
                equalTo(geometryAsGeoJson));
    }

    protected String toJson(Object value) throws IOException {
        return writer.writeValueAsString(value);
    }


    protected void assertRoundTrip(Geometry geom) throws IOException {
        String json = writer.writeValueAsString(geom);
        System.out.println(json);
        Geometry regeom = mapper.reader(Geometry.class).readValue(json);
        assertThat(regeom, equalTo(geom));
    }
}
