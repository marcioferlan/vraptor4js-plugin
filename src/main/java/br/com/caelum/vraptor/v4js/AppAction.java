package br.com.caelum.vraptor.v4js;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

/**
 * This class represents application actions. 
 * For VRaptor it means every public method within a Controller class.
 * 
 * @author Marcio Lima
 *
 */
public class AppAction {

	/** Action URL */
	private String url;

	/** GET, POST, DELETE, etc */
	private String httpMethod;

	/** Action method reference */
	private Method method;

	/** List of method parameters */
	private List<String> params = Lists.newArrayList();

	public AppAction(Method method) {
		this.method = method;
	}

	public AppAction addParams(final String... params) {
		this.params.addAll(Arrays.asList(params));
		return this;
	}

	public AppAction withHttpMethod(final String httpMethod) {
		this.httpMethod = httpMethod;
		return this;
	}

	public AppAction withUrl(final String url) {
		this.url = url;
		return this;
	}

	public String getName() {
		return method != null ? method.getName() : null;
	}

	public List<String> getParams() {
		return params;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public String getUrl() {
		return url;
	}

	public Method getMethod() {
		return method;
	}

	@Override
	public String toString() {
		return getName() + "(" + params + ")";
	}

}