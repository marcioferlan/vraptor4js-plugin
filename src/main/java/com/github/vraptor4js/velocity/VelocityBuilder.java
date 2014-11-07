package com.github.vraptor4js.velocity;

import java.io.StringWriter;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@ApplicationScoped
public class VelocityBuilder {
	
	public VelocityEngine engine;

	/**
	 * @deprecated CDI eyes-only
	 */
	protected VelocityBuilder() {
		this(null);
	}
	
	@Inject
	public VelocityBuilder(VelocityConfiguration configuration) {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, configuration.getResourceLoader());
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, configuration.getLogger());
		engine.init();
	}
	
	public String generate(Map<String, Object> params, String name) {
		VelocityContext context = new VelocityContext(params);
		try {
			StringWriter writer = new StringWriter();
			mergeTemplate(name, context, writer);
			writer.close();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void mergeTemplate(String name, VelocityContext context,
			StringWriter writer) {
		engine.getTemplate(name).merge(context, writer);
	}

}