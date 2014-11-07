package com.github.vraptor4js.velocity;

import java.io.StringWriter;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@ApplicationScoped
public class VelocityBuilder {
	
	public final VelocityEngine engine;

	/**
	 * @deprecated CDI eyes-only
	 */
	protected VelocityBuilder() {
		this(null);
	}
	
	public VelocityBuilder(VelocityConfiguration configuration) {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, configuration.getResourceLoader());
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, configuration.getLogger());
		engine.init();
	}

	private Template getTemplate(String name) {
		return engine.getTemplate(name);
	}
	
	private VelocityContext getContext(Map<String, Object> params) {
		return new VelocityContext(params);
	}

	private StringWriter getWriter() {
		return new StringWriter();
	}

	public String generate(Map<String, Object> params, String name) {
		final VelocityContext context = getContext(params);
		try {
			final Template jsController = getTemplate(name);
			final StringWriter writer = getWriter();
			jsController.merge(context, writer);
			writer.close();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}