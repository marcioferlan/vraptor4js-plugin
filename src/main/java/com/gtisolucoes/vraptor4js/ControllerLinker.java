package com.gtisolucoes.vraptor4js;

import java.lang.reflect.Method;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

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
	private final HttpServletRequest request;

	/**
	 * @deprecated CDI eyes-only
	 */
	protected ControllerLinker() {
		this(null, null, null);
	}
	
	@Inject
	public ControllerLinker(Router router, Configuration cfg, HttpServletRequest request) {
		this.router = router;
		this.cfg = cfg;
		this.request = request;
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
			// full url path
			final String url = cfg.getApplicationPath() + uri;
			// checking for X-Forwarded-Proto header
			final String X_Forwarded_Proto = request.getHeader("X-Forwarded-Proto");
			final String protocol = request.getScheme();
			// X-Forwarded-Proto header is different from actual request scheme (protocol)
			if (StringUtils.isNotEmpty(X_Forwarded_Proto) && !X_Forwarded_Proto.equals(protocol)) {
				// replace request scheme by t
				return url.replace(protocol, X_Forwarded_Proto);
			}
			return url;
		}
		return uri;
	}

}