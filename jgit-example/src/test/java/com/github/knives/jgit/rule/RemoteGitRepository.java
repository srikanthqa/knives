package com.github.knives.jgit.rule;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.junit.rules.TemporaryFolder;

public class RemoteGitRepository extends TemporaryFolder {
	
	private final String remoteUri;
	private Repository repository;
	
	public RemoteGitRepository(String remoteUri) {
		this.remoteUri = remoteUri;
	}
	
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
	
	private void createGitRepo() throws IOException, IllegalStateException, GitAPIException {
		repository = Git.cloneRepository()
			.setBare(false)
			.setCloneAllBranches(true)
			.setURI(remoteUri)
			.setDirectory(getRoot())
			.setProgressMonitor(new TextProgressMonitor())
			.call()
			.getRepository();
	}
	
	private void closeGitRepo() {
		repository.close();
	}
	
	public Repository getRepository() {
		return repository;
	}
}
