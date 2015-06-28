package com.github.kn.knives.javapoet;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import org.junit.Test;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class FlowControlTest {

	@Test
	public void test() throws IOException {
		MethodSpec main = MethodSpec.methodBuilder("main")
			    .addStatement("int total = 0")
			    .beginControlFlow("for (int i = 0; i < 10; i++)")
			    .addStatement("total += i")
			    .endControlFlow()
			    .build();
		
		TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(main)
				.build();

		JavaFile javaFile = JavaFile.builder("com.example.helloworld",
				helloWorld).build();

		javaFile.writeTo(System.out);
	}

}
