package com.github.knives.jgit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class FindGitRootDirectoryTest {

	@Test
	public void test() throws IOException {
		final FileRepositoryBuilder builder = new FileRepositoryBuilder();
		final Repository repository = builder.readEnvironment() 
				.findGitDir(new File("/tmp")) 
				.build();
				
		System.out.println(repository.getDirectory().getCanonicalPath());
	}

}
