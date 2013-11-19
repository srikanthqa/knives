package com.github.knives.gradle.custom.sourceset;

import org.gradle.api.file.SourceDirectorySet;
import org.gradle.language.base.LanguageSourceSet;
import org.gradle.util.Configurable;

public interface CustomSourceSet extends LanguageSourceSet, Configurable<SourceDirectorySet> {
	final public static String CUSTOM_SOURCE_NAME = "custom";
}
