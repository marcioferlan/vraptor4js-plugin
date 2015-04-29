package com.gtisolucoes.vraptor4js.velocity;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.google.common.collect.Maps;
import com.gtisolucoes.vraptor4js.AppAction;
import com.gtisolucoes.vraptor4js.ControllerLib;

/**
 * Responsible for generating the JS Controllers on-the-fly.
 * 
 * @author Marcio Lima
 *
 */
@RequestScoped
public class JsControllerGenerator {

	private static final String TEMPLATE_PATH = "/com/gtisolucoes/vraptor4js/template/controller-%s.vtl";

	private final VelocityBuilder velocityWrapper;

	private final ControllerLib lib;

	/**
	 * @deprecated CDI eyes-only
	 */
	protected JsControllerGenerator() {
		this(null, null);
	}

	@Inject
	public JsControllerGenerator(VelocityBuilder velocityWrapper, ControllerLib lib) {
		this.velocityWrapper = velocityWrapper;
		this.lib = lib;
	}

	/**
	 * Generates the JS Controller for the defined lib (angular [default] or jquery)
	 * @param ctrl 
	 * @param actions 
	 * @return
	 */
	public String generate(List<AppAction> actions, String ctrl) {
		final Map<String, Object> params = Maps.newHashMap();
		params.put("ctrl", ctrl);
		params.put("actions", actions);
		return velocityWrapper.generate(params, String.format(TEMPLATE_PATH, lib.get()));
	}

}