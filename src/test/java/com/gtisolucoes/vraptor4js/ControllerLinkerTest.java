package com.gtisolucoes.vraptor4js;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.gtisolucoes.vraptor4js.ControllerLinker;

import br.com.caelum.vraptor.config.Configuration;
import br.com.caelum.vraptor.http.route.Router;

public class ControllerLinkerTest {

	private ControllerLinker controllerLinker;
	@Mock private Router router;
	@Mock private Configuration cfg;
	@Mock private HttpServletRequest request;
	private Method method;
	private Class<MyController> controller;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		controller = MyController.class;
		method = controller.getMethod("myMethod");
		when(cfg.getApplicationPath()).thenReturn("/path");
		
		controllerLinker = new ControllerLinker(router, cfg, request);
	}

	@Test
	public void shouldReturnPath() {
		when(router.urlFor(controller, method)).thenReturn("/my/myMethod");
		
		assertThat(controllerLinker.linkTo(controller, method), equalTo("/path/my/myMethod"));
	}
	
	@Test
	public void shouldReturnRelativePath() {
		when(router.urlFor(controller, method)).thenReturn("my/myMethod");
		
		assertThat(controllerLinker.linkTo(controller, method), equalTo("my/myMethod"));
	}
	
	@Test
	public void shouldReplaceProtocol() {
		when(cfg.getApplicationPath()).thenReturn("http://path");
		when(router.urlFor(controller, method)).thenReturn("/my/myMethod");
		when(request.getHeader("X-Forwarded-Proto")).thenReturn("https");
		when(request.getScheme()).thenReturn("http");

		assertThat(controllerLinker.linkTo(controller, method), equalTo("https://path/my/myMethod"));
	}

	
}
