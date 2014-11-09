package br.com.caelum.vraptor.v4js;

import java.util.List;

import javax.inject.Inject;

import org.apache.tools.ant.filters.StringInputStream;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.observer.download.Download;
import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import br.com.caelum.vraptor.v4js.velocity.JsControllerGenerator;

@Controller
@Path("/v4js")
public class VRaptor4jsController {

	private static final String CONTENT_TYPE = "text/javascript; charset=UTF-8";

	private final JsControllerGenerator generator;

	private final ControllerRegistry controllersRegistry;

	private final ControllerLinker linker;

	private final ControllerLib lib;
	
	/**
	 * @deprecated CDI eyes-only
	 */
	protected VRaptor4jsController() {
		this(null, null, null, null);
	}

	@Inject
	public VRaptor4jsController(JsControllerGenerator generator,
			ControllerRegistry controllersRegistry, ControllerLinker linker,
			ControllerLib lib) {
		this.generator = generator;
		this.controllersRegistry = controllersRegistry;
		this.linker = linker;
		this.lib = lib;
	}



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
		final String javascript = generator.generate(actions, ctrl);
		final StringInputStream inputstream = new StringInputStream(javascript.toString());
		return new InputStreamDownload(inputstream, CONTENT_TYPE, ctrl, false, javascript.length());
	}
}