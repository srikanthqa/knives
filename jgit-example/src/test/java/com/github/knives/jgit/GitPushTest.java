package com.github.knives.jgit;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.TransportConfigCallback;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.TextProgressMonitor;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.Transport;
import org.junit.Rule;
import org.junit.Test;

import com.github.knives.jgit.auth.SystemPropertiesCredentialsProvider;
import com.github.knives.jgit.rule.ScratchpadGitRepository;

public class GitPushTest {

	@Rule
	public final ScratchpadGitRepository repository = new ScratchpadGitRepository();
	
	@Test
	public void test() throws GitAPIException, IOException {
		System.out.println(repository.getRepository());
		
		File newFile = repository.newFile();
		FileUtils.write(newFile, "test file");
		
		String relativePath = repository.relative(newFile).toString();
		
		Git git = new Git(repository.getRepository());
		git.add()
			.addFilepattern(relativePath)
			.call();
		
		RevCommit commit = git.commit()
				.setMessage("commit " + relativePath)
				.call();
		
		Iterable<PushResult> pushResults = git.push()
			.setTimeout(1000)
			.setProgressMonitor(new TextProgressMonitor(new PrintWriter(System.out)))
			.setTransportConfigCallback(new TransportConfigCallback() {
				@Override
				public void configure(Transport transport) {
					transport.setCredentialsProvider(new SystemPropertiesCredentialsProvider());
				}
			})
			.call();
		
		pushResults.forEach(System.out::println);
	}

}
