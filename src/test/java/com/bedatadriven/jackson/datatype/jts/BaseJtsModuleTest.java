package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by mihaildoronin on 11/11/15.
 */
public abstract class BaseJtsModuleTest<T extends Geometry> {
    protected GeometryFactory gf = new GeometryFactory();
    private ObjectWriter writer;
    private ObjectMapper mapper;
    private T geometry;
    private T geometryWithSrid;
    private String geometryAsGeoJson;
    private String geometryAsGeoJsonWithSrid;

    protected BaseJtsModuleTest() {
    }

    @Before
    public void setup() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JtsModule());
        writer = mapper.writer();
        geometry = createGeometry();
        geometryWithSrid = createGeometryWithSrid();
        geometryAsGeoJson = createGeometryAsGeoJson();
        geometryAsGeoJsonWithSrid = createGeometryAsGeoJsonWithSrid();
    }

    protected abstract Class<T> getType();

    protected abstract String createGeometryAsGeoJson();

    protected abstract T createGeometry();
    
    protected abstract String createGeometryAsGeoJsonWithSrid();
    
    protected abstract T createGeometryWithSrid();


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

    @Test
    public void shouldDeserializeConcreteTypeWithSrid() throws Exception {
        T concreteGeometry = mapper.readValue(geometryAsGeoJsonWithSrid, getType());
        assertThat(
                toJson(concreteGeometry),
                equalTo(geometryAsGeoJsonWithSrid));
    }

    @Test
    public void shouldDeserializeAsInterfaceWithSrid() throws Exception {
        assertRoundTrip(geometryWithSrid);
        assertThat(
                toJson(geometryWithSrid),
                equalTo(geometryAsGeoJsonWithSrid));
    }

    protected String toJson(Object value) throws IOException {
        return writer.writeValueAsString(value);
    }

    protected void assertRoundTrip(T geom) throws IOException {
        String json = writer.writeValueAsString(geom);
        System.out.println(json);
        Geometry regeom = mapper.reader(Geometry.class).readValue(json);
        assertThat(geom.equalsExact(regeom), is(true));
    }
}
