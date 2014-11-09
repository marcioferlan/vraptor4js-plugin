package br.com.caelum.vraptor.v4js;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

/**
 * This component is responsible for holding the JS library to use.
 * Angular JS is the default value, but jQuery is also available.
 * 
 * @author Marcio Lima
 *
 */
@ApplicationScoped
public class ControllerLib implements Serializable {

	private static final long serialVersionUID = 7905955221131628107L;

	public static final String LIB_ANGULAR = "angular";
	public static final String LIB_JQUERY = "jquery";

	private String lib;
	
	/**
	 * Sets the JS library
	 * @param lib
	 */
	public void set(String lib) {
		this.lib = lib;
	}

	/**
	 * Returns the JS library (or angular [default] if null)
	 * @return
	 */
	public String get() {
		return lib != null ? lib : LIB_ANGULAR;
	}
}
