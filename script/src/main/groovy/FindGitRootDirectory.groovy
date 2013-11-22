@Grapes([
	@Grab(group='org.eclipse.jgit', module='org.eclipse.jgit', version='3.1.0.201310021548-r')
])

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

FileRepositoryBuilder builder = new FileRepositoryBuilder();
Repository repository = builder
		.readEnvironment() // scan environment GIT_* variables
		.findGitDir() // scan up the file system tree
		.build();
		
println repository.directory