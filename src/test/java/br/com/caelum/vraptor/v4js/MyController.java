package br.com.caelum.vraptor.v4js;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.v4js.V4js;

@Controller
public class MyController {

		@V4js
		public void myMethod() {}
		public void myMethodWithoutAnnotation() {}
	
}
