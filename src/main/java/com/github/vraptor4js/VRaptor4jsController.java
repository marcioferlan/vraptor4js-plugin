package com.github.vraptor4js;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.tools.ant.filters.StringInputStream;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;

import com.github.vraptor4js.velocity.JsControllerGenerator;
import com.google.common.collect.Maps;

@Controller
@Path("/v4js")
public class VRaptor4jsController {

	private static final String CONTENT_TYPE = "text/javascript; charset=UTF-8";

	@Inject
	private JsControllerGenerator generator;

	@Inject
	private ControllerRegistry controllersRegistry;

	@Inject
	private ControllerLinker linker;

	@Inject
	private ControllerLib lib;

	/**
	 * Action that generates the JS controller dinamically
	 * @param ctrl
	 * @return
	 */
	@Get("/{lib}/{ctrl}")
	public Download controller(String lib, String ctrl) {
		this.lib.set(lib);
		final List<AppAction> actions = controllersRegistry.getActions(ctrl);
		if (actions != null) {
			for (final AppAction action : actions) {
				final String url = linker.linkTo(action.getMethod().getDeclaringClass(), action.getMethod());
				action.withUrl(url);
			}
		}
		return download(actions, ctrl);
	}

	private Download download(List<AppAction> actions, String ctrl) {
		final Map<String, Object> params = Maps.newHashMap();
		params.put("ctrl", ctrl);
		params.put("actions", actions);
		final String javascript = generator.generate(params);
		final StringInputStream inputstream = new StringInputStream(javascript.toString());
		return new InputStreamDownload(inputstream, CONTENT_TYPE, ctrl, false, javascript.length());
	}
}