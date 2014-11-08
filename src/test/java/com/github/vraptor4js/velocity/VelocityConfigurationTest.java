package com.github.vraptor4js.velocity;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VelocityConfigurationTest {

	private VelocityConfiguration cfg;
	
	@Before
	public void setUp() throws Exception {
		cfg = new VelocityConfiguration();
	}

	@Test
	public void shouldReturnResourceLoader() {
		assertThat(cfg.getResourceLoader(), equalTo("classpath"));
	}
	
	@Test
	public void shouldReturnNullLog() {
		assertThat(cfg.getLogger(), equalTo("org.apache.velocity.runtime.log.NullLogSystem"));
	}

}
