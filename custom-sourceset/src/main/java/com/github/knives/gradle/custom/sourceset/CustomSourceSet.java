package com.github.knives.gradle.custom.sourceset;

import org.gradle.language.base.LanguageSourceSet;

/**
 * 
 * @author khai
 * 
 * Without delegate with Configurable, you have one extra level call source
 * 
 * sources {
 *		main {
 *			custom {
 *				source {
 *					srcDir 'src/main/custom'
 * 				}
 *			}
 *		}
 * }
 * 
 * With Configurable to delegate to SourceDirectorySet, only 3 levels
 * 
 * sources {
 *		main {
 *			custom {
 *				srcDir 'src/main/custom'
 *			}
 *		}
 * }
 */
public interface CustomSourceSet extends LanguageSourceSet /*, Configurable<SourceDirectorySet> */ {
	final public static String CUSTOM_SOURCE_NAME = "custom";
}
