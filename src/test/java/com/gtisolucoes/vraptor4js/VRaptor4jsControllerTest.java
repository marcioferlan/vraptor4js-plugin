package com.gtisolucoes.vraptor4js;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gtisolucoes.vraptor4js.AppAction;
import com.gtisolucoes.vraptor4js.ControllerLib;
import com.gtisolucoes.vraptor4js.ControllerLinker;
import com.gtisolucoes.vraptor4js.ControllerRegistry;
import com.gtisolucoes.vraptor4js.VRaptor4jsController;
import com.gtisolucoes.vraptor4js.velocity.JsControllerGenerator;

import br.com.caelum.vraptor.observer.download.Download;

public class VRaptor4jsControllerTest {

	private VRaptor4jsController controller;
	
	private ControllerLib lib;
	@Mock private JsControllerGenerator generator;
	@Mock private ControllerRegistry controllersRegistry;
	@Mock private ControllerLinker linker;
	private Method method;
	private List<AppAction> actions;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		lib = new ControllerLib();
		method = MyController.class.getMethod("myMethod");
		
		actions = Arrays.asList(new AppAction(method));
		when(controllersRegistry.getActions("MyController")).thenReturn(actions);
		when(linker.linkTo(MyController.class, method)).thenReturn("/path/my/myMethod");
		when(generator.generate(actions, "MyController")).thenReturn("function(){}");
		
		controller = new VRaptor4jsController(generator, controllersRegistry, linker, lib);
	}

	@Test
	public void shouldReturnDownload() {
		Download download = controller.controller("jquery", "MyController");
		assertThat(download, instanceOf(Download.class));
	}

}
