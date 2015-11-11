package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

public class JtsModule extends SimpleModule {

	public JtsModule() {
		super("JtsModule", new Version(1, 0, 0, null,"com.bedatadriven","jackson-datatype-jts"));

		addSerializer(Geometry.class, new GeometrySerializer());
		addDeserializer(Geometry.class, new GeometryDeserializer());
//        addDeserializer(Point.class, new PointDeserializer());
	}

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
    }
}
