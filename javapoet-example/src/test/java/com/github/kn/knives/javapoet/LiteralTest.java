package com.github.kn.knives.javapoet;

import static org.junit.Assert.fail;

import java.io.IOException;

import javax.lang.model.element.Modifier;

import org.junit.Test;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

public class LiteralTest {

	@Test
	public void test() throws IOException {
		MethodSpec main = computeRange("main", 1, 100, "*");
		
		TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
				.addModifiers(Modifier.PUBLIC, Modifier.FINAL).addMethod(main)
				.build();

		JavaFile javaFile = JavaFile.builder("com.example.helloworld",
				helloWorld).build();

		javaFile.writeTo(System.out);
	}

	private MethodSpec computeRange(String name, int from, int to, String op) {
		return MethodSpec.methodBuilder(name)
				.returns(int.class)
				.addStatement("int result = 0")
				.beginControlFlow("for (int i = $L; i < $L; i++)", from, to)
				.addStatement("result = result $L i", op).endControlFlow()
				.addStatement("return result").build();
	}
}
