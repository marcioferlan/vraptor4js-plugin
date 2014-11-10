package com.gtisolucoes.vraptor4js.cors;

import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Options;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.controller.HttpMethod;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.view.Results;

/**
 * Controller to provide Options method for CORS (see {@link http://pt.wikipedia.org/wiki/Cross-origin_resource_sharing} for details)
 * 
 * @author Marcio Lima
 *
 */
@Controller
public class CORSController {

	@Inject
	private Result result;

	@Inject
	private Router router;

	@Inject
	private HttpServletRequest request;

	@Options("/*")
	public void options() {
		final Set<HttpMethod> allowed = router.allowedMethodsFor(request.getRequestURI());
		final String allowMethods = allowed.toString().replaceAll("\\[|\\]", "");
		result.use(Results.status()).header("Allow", allowMethods);
		result.use(Results.status()).header("Access-Control-Allow-Methods", allowMethods);
		result.use(Results.status()).header("Access-Control-Allow-Headers", "Content-Type, accept, Authorization, origin, X-Requested-With");
		result.use(Results.status()).noContent();
	}
}