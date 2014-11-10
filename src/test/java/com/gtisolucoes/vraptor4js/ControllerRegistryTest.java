package com.gtisolucoes.vraptor4js;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

import org.junit.Before;
import org.junit.Test;

import com.gtisolucoes.vraptor4js.ControllerRegistry;

public class ControllerRegistryTest {

	private ControllerRegistry controllerRegistry;

	@Before
	public void setUp() throws Exception {
		controllerRegistry = new ControllerRegistry();
	}

	@Test
	public void shouldRegistryAction() {
		ProcessAnnotatedType<MyController> type = new ProcessAnnotatedType<MyController>() {
			public AnnotatedType<MyController> getAnnotatedType() {
				return new AnnotatedType<MyController>() {
					@Override public Type getBaseType() {return null;}
					@Override public Set<Type> getTypeClosure() {return null;}
					@Override public <T extends Annotation> T getAnnotation(Class<T> annotationType) {return null;}
					@Override public Set<Annotation> getAnnotations() {return null;}
					@Override public Set<AnnotatedField<? super MyController>> getFields() {return null;}
					@Override public Set<AnnotatedConstructor<MyController>> getConstructors() {return null;}
					@Override public Class<MyController> getJavaClass() {return MyController.class;}
					@Override public Set<AnnotatedMethod<? super MyController>> getMethods() {return new HashSet<>();}
					@Override public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {return true;}
				};
			}
			public void veto() {}
			@Override public void setAnnotatedType(AnnotatedType<MyController> type) {}
		};
		
		controllerRegistry.scanControllers(type);
		
		assertThat(controllerRegistry.getActions("MyController").get(0).getName(), equalTo("myMethod"));
		assertThat(controllerRegistry.getActions("MyController").size(), equalTo(1));
	}
}
