package org.ws.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.ws.resources.RestEndPoint;

@ApplicationPath("/api")
public class GraphApplication extends Application {

	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(RestEndPoint.class);
		return classes;

	}
}
