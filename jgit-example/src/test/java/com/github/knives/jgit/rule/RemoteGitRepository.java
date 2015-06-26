package com.github.knives.jgit.rule;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

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
			.setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
			.call()
			.getRepository();
	}
	
	private void closeGitRepo() {
		repository.close();
	}
	
	public Repository getRepository() {
		return repository;
	}
	
	public Path relative(File childFile) {
		Path rootPath = Paths.get(getRoot().getAbsolutePath());
		Path childPath = Paths.get(childFile.getAbsolutePath());
		return rootPath.relativize(childPath);
	}
}
