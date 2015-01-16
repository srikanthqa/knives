package com.github.knives.jgit;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Ignore;
import org.junit.Test;

public class OpenRepositoryTest {

	@Test
	@Ignore
	public void test() throws IOException, GitAPIException {
		// first create a test-repository, the return is including the .get directory here!
        File repoDir = createSampleGitRepo();
        
        // now open the resulting repository with a FileRepositoryBuilder
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = builder.setGitDir(repoDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();

        System.out.println("Having repository: " + repository.getDirectory());

        // the Ref holds an ObjectId for any type of object (tree, commit, blob, tree)
        Ref head = repository.getRef("refs/heads/master");
        System.out.println("Ref of refs/heads/master: " + head);

        repository.close();
    }

    private static File createSampleGitRepo() throws IOException, GitAPIException {
        Repository repository = FileRepositoryBuilder.create(new File("/tmp/test", ".git"));
        repository.create();
        
        System.out.println("Temporary repository at " + repository.getDirectory());

        // create the file
        File myfile = new File(repository.getDirectory().getParent(), "testfile");
        myfile.createNewFile();

        // run the add-call
        new Git(repository).add()
                .addFilepattern("testfile")
                .call();


        // and then commit the changes
        new Git(repository).commit()
                .setMessage("Added testfile")
                .call();
        
        System.out.println("Added file " + myfile + " to repository at " + repository.getDirectory());
        
        File dir = repository.getDirectory();
        
        repository.close();
        
        return dir;
    }
}
