package com.gtisolucoes.vraptor4js;

import com.gtisolucoes.vraptor4js.V4js;

import br.com.caelum.vraptor.Controller;

@Controller
public class MyController {

		@V4js
		public void myMethod() {}
		public void myMethodWithoutAnnotation() {}
	
}
