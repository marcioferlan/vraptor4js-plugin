package br.com.caelum.vraptor.v4js.velocity;

import javax.enterprise.context.ApplicationScoped;

/**
 * Velocity template engine configurations.
 * 
 * @author Marcio Lima
 *
 */
@ApplicationScoped
public class VelocityConfiguration {

	public String getResourceLoader() {
		return "classpath";
	}

	public String getLogger() {
		return "org.apache.velocity.runtime.log.NullLogSystem";
	}
}