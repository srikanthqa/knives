package com.github.knives.jgit.rule;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.rules.TemporaryFolder;

public class LocalGitRepository extends TemporaryFolder {
	
	private Repository repository;
	
	@Override
	protected void before() throws Throwable {
		create();
		createGitRepo();
	};

	@Override
	protected void after() {
		closeGitRepo();
		delete();
	}
	
	private void createGitRepo() throws IOException {
        repository = FileRepositoryBuilder.create(new File(getRoot(), ".git"));
        repository.create(false);
	}
	
	private void closeGitRepo() {
		repository.close();
	}
	
	public Repository getRepository() {
		return repository;
	}
}
