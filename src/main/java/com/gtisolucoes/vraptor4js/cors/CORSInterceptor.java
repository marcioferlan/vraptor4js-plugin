package com.gtisolucoes.vraptor4js.cors;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.BeforeCall;
import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;

/**
 * Interceptor for enabling CORS (see {@link http://pt.wikipedia.org/wiki/Cross-origin_resource_sharing} for datails)
 * 
 * @author Marcio Lima
 *
 */
@Intercepts
public class CORSInterceptor {

	@Inject
	private ServletContext servletContext;

	@Inject
	private HttpServletResponse response;

	@BeforeCall
	public void intercept() throws InterceptionException {
		addHeader("Access-Control-Allow-Origin");
		addHeader("Access-Control-Allow-Credentials");
		addHeader("Access-Control-Expose-Headers");
	}

	private void addHeader(String header) {
		if (initParam(header) != null) {
			response.addHeader(header, initParam(header));
		}
	}

	private String initParam(String name) {
		return servletContext.getInitParameter(name);
	}
}