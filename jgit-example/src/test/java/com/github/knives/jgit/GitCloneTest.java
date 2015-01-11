package com.github.knives.jgit;

import java.io.File;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.junit.Test;

public class GitCloneTest {

	@Test
	public void test() throws InvalidRemoteException, TransportException, GitAPIException {
		Git.cloneRepository()
		.setBare(false)
		.setCloneAllBranches(true)
		.setURI("http://github.com")
		.setDirectory(new File("/tmp"))
		.setProgressMonitor(new TextProgressMonitor())
		.call();
	}

}
