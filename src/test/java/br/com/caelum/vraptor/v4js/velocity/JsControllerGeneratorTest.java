package br.com.caelum.vraptor.v4js.velocity;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.caelum.vraptor.v4js.AppAction;
import br.com.caelum.vraptor.v4js.ControllerLib;
import br.com.caelum.vraptor.v4js.MyController;
import br.com.caelum.vraptor.v4js.velocity.JsControllerGenerator;
import br.com.caelum.vraptor.v4js.velocity.VelocityBuilder;

public class JsControllerGeneratorTest {

	private JsControllerGenerator generator;
	private List<AppAction> actions;
	private String ctrl;
	@Mock private VelocityBuilder wrapper;
	@Mock private Template template;
	@Mock private Map<String, Object> params;
	@Mock private StringWriter writter;
	@Mock private VelocityContext context;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		ctrl = "MyController";
		actions = Arrays.asList(new AppAction(MyController.class.getMethod("myMethod")));
		
		when(wrapper.generate(anyMapOf(String.class, Object.class), anyString())).thenReturn("javascript");
		
		generator = new JsControllerGenerator(wrapper, new ControllerLib());
	}

	@Test
	public void shouldGenerate() {
		assertThat(generator.generate(actions, ctrl), equalTo("javascript"));
	}
	
}
