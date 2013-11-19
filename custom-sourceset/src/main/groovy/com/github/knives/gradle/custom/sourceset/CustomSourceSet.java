package com.github.knives.gradle.custom.sourceset;

import org.gradle.api.file.SourceDirectorySet;
import org.gradle.language.base.LanguageSourceSet;
import org.gradle.util.Configurable;

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
 *					srcDir 'src/main/css'
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
 *				srcDir 'src/main/css'
 *			}
 *		}
 * }
 */
public interface CustomSourceSet extends LanguageSourceSet /*, Configurable<SourceDirectorySet> */ {
	final public static String CUSTOM_SOURCE_NAME = "custom";
}
