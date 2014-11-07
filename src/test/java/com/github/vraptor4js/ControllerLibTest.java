package com.github.vraptor4js;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class ControllerLibTest {

	private ControllerLib controllerLib;
	
	@Before
	public void setUp() throws Exception {
		controllerLib = new ControllerLib();
	}

	@Test
	public void shouldSetLib() {
		controllerLib.set("jquery");
		assertThat(controllerLib.get(), equalTo("jquery"));
	}
}
