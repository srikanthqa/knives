package com.github.knives.io.ignore;

import static java.lang.annotation.ElementType.*;
import java.lang.annotation.Target;

@Target({ANNOTATION_TYPE}) 
public @interface TestAnnotation {

	@Target({METHOD})
	public static @interface TestAnnotationMethod { }
	
	@Target({CONSTRUCTOR})
	public static @interface TestAnnotationConstructor {}
	
	@Target({FIELD})
	public static @interface TestAnnotationField {}
	
	@Target({TYPE})
	public static @interface TestAnnotationType {}
	
	@Target({PARAMETER})
	public static @interface TestAnnotationParameter {}
}
