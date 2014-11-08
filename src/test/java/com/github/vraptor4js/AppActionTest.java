package com.github.vraptor4js;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;

public class AppActionTest {

	private AppAction appAction;
	private Method method;
	
	@Before
	public void setUp() throws Exception {
		method = MyController.class.getMethod("myMethod");
		appAction = new AppAction(method);
	}

	@Test
	public void shouldReturnMethod() {
		assertThat(appAction.getMethod(), equalTo(method));
	}
	
	@Test
	public void shouldReturnTextAsJavascriptMethod() {
		assertThat(appAction.toString(), equalTo("myMethod([])"));
	}

	@Test
	public void shouldReturnMethodName() {
		assertThat(appAction.getName(), equalTo("myMethod"));
	}
	
	@Test
	public void shouldReturnNullIfMethodIsNull() {
		appAction = new AppAction(null);
		
		assertThat(appAction.getName(), nullValue());
	}
	
	@Test
	public void shouldSetHttpMethod() {
		appAction.withHttpMethod("post");
		
		assertThat(appAction.getHttpMethod(), equalTo("post"));
	}
	
	@Test
	public void shouldAddParams() {
		appAction.addParams("param1", "param2");
		
		assertThat(appAction.getParams().get(0), equalTo("param1"));
		assertThat(appAction.getParams().get(1), equalTo("param2"));
	}
	
	@Test
	public void shouldSetUrl() {
		appAction.withUrl("http://localhost");
		
		assertThat(appAction.getUrl(), equalTo("http://localhost"));
	}
}
