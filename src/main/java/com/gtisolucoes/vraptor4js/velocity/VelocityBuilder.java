package com.gtisolucoes.vraptor4js.velocity;

import java.io.StringWriter;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

@ApplicationScoped
public class VelocityBuilder {
	
	private VelocityEngine engine;
	private final VelocityConfiguration configuration;

	/**
	 * @deprecated CDI eyes-only
	 */
	protected VelocityBuilder() {
		this(null);
	}
	
	@Inject
	public VelocityBuilder(VelocityConfiguration configuration) {
		this.configuration = configuration;
	}

	@PostConstruct
	public void setUp() {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, configuration.getResourceLoader());
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, configuration.getLogger());
		engine.init();
	}
	
	public String generate(Map<String, Object> params, String name) {
		try {
			StringWriter writer = new StringWriter();
			mergeTemplate(name, params, writer);
			writer.close();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void mergeTemplate(String name, Map<String, Object> params,
			StringWriter writer) {
		Template template = engine.getTemplate(name);
		template.merge(new VelocityContext(params), writer);
	}

}