package com.github.vraptor4js;

import java.lang.reflect.Method;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import br.com.caelum.vraptor.config.Configuration;
import br.com.caelum.vraptor.http.route.Router;

/**
 * This class is responsible for determining the route of a controller action in the application.
 * 
 * @author Marcio Lima
 *
 */
@Dependent
public class ControllerLinker {

	private final Router router;
	private final Configuration cfg;

	/**
	 * @deprecated CDI eyes-only
	 */
	protected ControllerLinker() {
		this(null, null);
	}
	
	@Inject
	public ControllerLinker(Router router, Configuration cfg) {
		this.router = router;
		this.cfg = cfg;
	}

	/**
	 * Returns the URL for the given controller method
	 * @param controller
	 * @param method
	 * @return
	 */
	public String linkTo(final Class<?> controller, final Method method) {
		String uri = router.urlFor(controller, method, new Object[method.getParameterTypes().length]);
		if (uri.startsWith("/")) {
			return cfg.getApplicationPath() + uri;
		}
		return uri;
	}

}