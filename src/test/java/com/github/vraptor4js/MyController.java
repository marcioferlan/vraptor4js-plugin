package com.github.vraptor4js;

import br.com.caelum.vraptor.Controller;

@Controller
public class MyController {

		@V4js
		public void myMethod() {}
		public void myMethodWithoutAnnotation() {}
	
}
