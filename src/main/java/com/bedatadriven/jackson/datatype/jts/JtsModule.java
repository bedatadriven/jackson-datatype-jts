package com.bedatadriven.jackson.datatype.jts;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

public class JtsModule extends SimpleModule {
	private static final long serialVersionUID = -6046431936495229027L;

	public JtsModule() {
		this(null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public JtsModule(GeometryFactory factory) {
		super("JtsModule", new Version(1, 0, 0, null,"com.bedatadriven","jackson-datatype-jts"));

		addSerializer(Geometry.class, new GeometrySerializer());
		GeometryDeserializer deser = new GeometryDeserializer();
		addDeserializer(Geometry.class, deser);
		addDeserializer(GeometryCollection.class, deser);
		addDeserializer(Point.class, deser);
		addDeserializer(MultiPoint.class, deser);
		addDeserializer(LineString.class, deser);
		addDeserializer(MultiLineString.class, deser);
		addDeserializer(MultiPolygon.class, deser);
		addDeserializer(Polygon.class, deser);
		addDeserializer(GeometryCollection.class, deser);
	}
}
