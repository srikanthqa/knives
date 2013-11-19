package com.github.knives.gradle.custom.sourceset;

import javax.inject.Inject;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.internal.file.FileResolver;
import org.gradle.internal.reflect.Instantiator;
import org.gradle.language.base.FunctionalSourceSet;
import org.gradle.language.base.ProjectSourceSet;
import org.gradle.language.base.internal.DefaultProjectSourceSet;
import org.gradle.language.base.plugins.LanguageBasePlugin;

class CustomSourceSetPlugin implements Plugin<Project> {
	private final Instantiator instantiator;
	private final FileResolver fileResolver;
	
	@Inject
	public CustomSourceSetPlugin(Instantiator instantiator, FileResolver fileResolver) {
		this.instantiator = instantiator;
		this.fileResolver = fileResolver;
	}
	
	@Override
	public void apply(final Project project) {
		project.getPlugins().apply(LanguageBasePlugin.class);

		final ProjectSourceSet projectSourceSet = project.getExtensions().getByType(DefaultProjectSourceSet.class);

		/**
		 * sources {
		 *     // FunctionalSourceSet functionalSourceSet
		 *     // assert functionalSourceSet.getName() == 'main'
		 *     main {
		 *         // functionalSourceSet.create("custom", Class<T> type)
		 *         // after register factory with that type
		 *         custom {
		 *         	    // CustomSourceSet.configure() delegate to SourceDirectorySet
		 *         }
		 *     }
		 * }
		 */
		projectSourceSet.all(new Action<FunctionalSourceSet>() {
			public void execute(final FunctionalSourceSet functionalSourceSet) {
				// create factory with parent name
				final CustomSourceSetFactory customSourceSetFactory = instantiator.newInstance(
						CustomSourceSetFactory.class, instantiator, fileResolver, functionalSourceSet.getName());
				
				// register factory with type
				functionalSourceSet.registerFactory(CustomSourceSet.class, customSourceSetFactory);
				
				// Add a single custom sourceset under parent functionalSourceSet
				// cann't call create with type in gradle file
				functionalSourceSet.create(CustomSourceSet.CUSTOM_SOURCE_NAME, CustomSourceSet.class);
			}
		});
	}
}
