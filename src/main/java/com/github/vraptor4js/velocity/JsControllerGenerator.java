package com.github.vraptor4js.velocity;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.github.vraptor4js.AppAction;
import com.github.vraptor4js.ControllerLib;
import com.google.common.collect.Maps;

/**
 * Responsible for generating the JS Controllers on-the-fly.
 * 
 * @author Marcio Lima
 *
 */
@RequestScoped
public class JsControllerGenerator {

	private static final String TEMPLATE_PATH = "/com/github/vraptor4js/template/controller-%s.vtl";

	private VelocityEngine engine;
	
	@Inject
	private ControllerLib lib;

	@Deprecated
	// CDI only
	public JsControllerGenerator() {
	}

	@Inject
	public JsControllerGenerator(VelocityConfiguration configuration) {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, configuration.getResourceLoader());
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, configuration.getLogger());
		engine.init();
	}

	/**
	 * Generates the JS Controller for the defined lib (angular [default] or jquery)
	 * @param ctrl 
	 * @param actions 
	 * @param params
	 * @return
	 */
	public String generate(List<AppAction> actions, String ctrl) {
		final Map<String, Object> params = Maps.newHashMap();
		params.put("ctrl", ctrl);
		params.put("actions", actions);
		
		final VelocityContext context = new VelocityContext(params);
		try {
			final Template jsController = engine.getTemplate(String.format(TEMPLATE_PATH, lib.get()));
			final StringWriter writer = new StringWriter();
			jsController.merge(context, writer);
			writer.close();
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}