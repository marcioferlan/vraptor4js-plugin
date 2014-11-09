package br.com.caelum.vraptor.v4js.velocity;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.v4js.velocity.VelocityBuilder;
import br.com.caelum.vraptor.v4js.velocity.VelocityConfiguration;

import com.google.common.collect.Maps;

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
		
		String generate = builder.generate(params, "/br/com/caelum/vraptor/v4js/template/controller-jquery.vtl");
		
		assertThat(generate, containsString("var v4jsErr = function(error"));
		assertThat(generate, containsString("var MyController = {"));
	}

}
