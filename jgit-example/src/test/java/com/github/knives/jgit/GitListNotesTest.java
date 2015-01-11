package com.github.knives.jgit;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class GitListNotesTest {

	@Test
	public void test() throws IOException, GitAPIException {
		final FileRepositoryBuilder builder = new FileRepositoryBuilder();
		final Repository repository = builder.readEnvironment() 
				.findGitDir(new File("/tmp")) 
				.build();
				
		new Git(repository).notesList().call().stream().forEach(note -> {
			System.out.println("Note: ${note} ${note.getName()} ${note.getData().getName()}");
			System.out.println("Content: ");
			
			// displaying the contents of the note is done via a simple blob-read
			try {
				ObjectLoader loader = repository.open(note.getData());
				loader.copyTo(System.out);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		repository.close();
	}

}
