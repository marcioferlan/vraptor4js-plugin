package com.gtisolucoes.vraptor4js.velocity;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.gtisolucoes.vraptor4js.velocity.VelocityBuilder;
import com.gtisolucoes.vraptor4js.velocity.VelocityConfiguration;

public class VelocityBuilderTest {

	private VelocityBuilder builder;
	private VelocityConfiguration cfg;
	
	@Before
	public void setUp() throws Exception {
		cfg = new VelocityConfiguration();
		
		builder = new VelocityBuilder(cfg);
		builder.setUp();
	}

	@Test
	public void shouldWriteTemplate() throws NoSuchMethodException, SecurityException {
		final Map<String, Object> params = Maps.newHashMap();
		params.put("ctrl", "MyController");
		
		String generate = builder.generate(params, "/com/gtisolucoes/vraptor4js/template/controller-jquery.vtl");
		
		assertThat(generate, containsString("var v4jsErr = function(error"));
		assertThat(generate, containsString("var MyController = {"));
	}

}
