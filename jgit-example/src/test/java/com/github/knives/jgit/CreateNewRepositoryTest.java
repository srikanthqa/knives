package com.github.knives.jgit;

import java.io.File;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class CreateNewRepositoryTest {

	@Test
	public void test() throws Exception {
		 // prepare a new folder
        File localPath = File.createTempFile("TestGitRepository", "");
        localPath.delete();

        // create the directory
        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create(false);

        System.out.println("Having repository: " + repository.getDirectory());

        repository.close();
    }

}
