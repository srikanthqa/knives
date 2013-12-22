package com.github.knives.script
import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.ObjectLoader
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.notes.Note
import org.eclipse.jgit.storage.file.FileRepositoryBuilder

final def cli = new CliBuilder(usage: 'GitListNotes -d <path/to/repository>')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.d( longOpt: 'dir', argName: 'd', required: true, args: 1, 'repo directory')
//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage()
	return
}

final FileRepositoryBuilder builder = new FileRepositoryBuilder()
final Repository repository = builder.readEnvironment() 
		.findGitDir(new File(opt.d)) 
		.build()
		
new Git(repository).notesList().call().each { final note ->
	println "Note: ${note} ${note.getName()} ${note.getData().getName()}"
	println "Content: "
	
	// displaying the contents of the note is done via a simple blob-read
	ObjectLoader loader = repository.open(note.getData())
	loader.copyTo(System.out)
}

repository.close()