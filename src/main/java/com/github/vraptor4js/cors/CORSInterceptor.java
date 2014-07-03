package com.github.vraptor4js.cors;

import javax.inject.Inject;
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
	private HttpServletResponse response;

	@BeforeCall
	public void intercept() throws InterceptionException {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Credentials", "true");
		response.addHeader("Access-Control-Expose-Headers", "Content-Type, Location, X-Requested-With");
	}
}