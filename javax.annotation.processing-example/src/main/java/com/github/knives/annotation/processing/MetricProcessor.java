package com.github.knives.annotation.processing;

import java.lang.reflect.Method;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes("com.github.knives.annotation.processing.Metric")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MetricProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations,
			RoundEnvironment roundEnv) {

		StringBuilder message = new StringBuilder();

        for (Element elem : roundEnv.getElementsAnnotatedWith(Metric.class)) {
            Metric implementation = elem.getAnnotation(Metric.class);

            message.append("Methods found in " + elem.getSimpleName().toString() + ":\n");

            for (Method method : implementation.getClass().getMethods()) {
                message.append(method.getName() + "\n");
            }

            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, message.toString());
        }

        return false; // allow others to process this annotation type
	}

}
