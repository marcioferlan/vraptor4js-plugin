package br.com.caelum.vraptor.v4js;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.http.Parameter;
import br.com.caelum.vraptor.http.ParameterNameProvider;
import br.com.caelum.vraptor.http.ParanamerNameProvider;

import com.google.common.collect.Maps;

/**
 * Component that registers all application controllers in order to identify
 * the application actions to publish in the JS controllers.
 * 
 * @author Marcio Lima
 *
 */
@ApplicationScoped
public class ControllerRegistry implements Extension {

	private final ParanamerNameProvider provider = new ParanamerNameProvider();

	private final Map<String, List<AppAction>> actions = Maps.newHashMap();

	/**
	 * Scans the application controllers
	 * @param pat
	 */
	public void scanControllers(@Observes ProcessAnnotatedType<?> pat) {
		final AnnotatedType<?> type = pat.getAnnotatedType();
		if (type.isAnnotationPresent(Controller.class)) {
			for (final Method method : type.getJavaClass().getDeclaredMethods()) {
				if (isEligible(method)) {
					registerAction(provider, method);
				}
			}
		}
	}

	/**
	 * Registers the method as an application action
	 * @param provider
	 * @param method
	 */
	private void registerAction(final ParameterNameProvider provider, final Method method) {
		final AppAction action = new AppAction(method).withHttpMethod(getHttpMethod(method));
		final Parameter[] parameters = provider.parametersFor(method);
		for (final Parameter param : parameters) {
			action.addParams(param.getName());
		}
		final Class<?> controller = method.getDeclaringClass();
		List<AppAction> list = actions.get(controller.getSimpleName());
		if (list == null) {
			list = new ArrayList<AppAction>();
			actions.put(controller.getSimpleName(), list);
		}
		list.add(action);
	}

	/**
	 * Only GET and POST methods are supported as of now
	 * @param method
	 * @return
	 */
	private String getHttpMethod(final Method method) {
		Get get = method.getAnnotation(Get.class);
		if (get != null) {
			return "get";
		}
		return "post";
	}

	/**
	 * Determines whether a method is eligible to be exposed in the JS controller
	 * @param method
	 * @return
	 */
	private boolean isEligible(final Method method) {
		return Modifier.isPublic(method.getModifiers()) && 
				!Modifier.isStatic(method.getModifiers()) && 
				(method.getAnnotation(V4js.class) != null || method.getDeclaringClass().getAnnotation(V4js.class) != null);
	}

	/**
	 * Returns the actions of a given controller
	 * @param controller
	 * @return
	 */
	public List<AppAction> getActions(final String controller) {
		return actions.get(controller);
	}

}