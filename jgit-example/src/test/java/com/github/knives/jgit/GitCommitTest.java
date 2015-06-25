package com.github.knives.jgit;

import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.junit.Rule;
import org.junit.Test;

import com.github.knives.jgit.rule.LocalGitRepository;

public class GitCommitTest {

	@Rule
	public final LocalGitRepository localGitRepository = new LocalGitRepository();
	
	
	@Test
	public void test() throws GitAPIException, IOException {
		Git git = new Git(localGitRepository.getRepository());
		git.add()
			.addFilepattern(localGitRepository.newFile("test").getName())
			.call();
		
		RevCommit commit = git.commit()
				.setMessage("test commit")
				.call();

		walkCommitToParent(commit);
		
		Ref branchRef = git.branchCreate()
			.setName("test-branch")
			.call();
		
		git.add()
			.addFilepattern(localGitRepository.newFile("test1").getName())
			.call();
		
		RevCommit commit2 = git.commit()
				.setMessage("test commit2")
				.call();
		
		walkCommitToParent(commit2);
		
		git.checkout().setStartPoint("master").call();
	}
	
	private void walkCommitToParent(RevCommit commit) throws GitAPIException, IOException {
		RevWalk revWalk = new RevWalk(localGitRepository.getRepository());
		revWalk.sort(RevSort.TOPO, true);
		revWalk.sort(RevSort.REVERSE, true);
		revWalk.markStart(commit);
		
		revWalk.forEach(it -> System.out.println(
				String.format("author: %s\nhash: %s\nmessage: %s\n\n",
						it.getAuthorIdent().getName(),
						it.name(),
						it.getFullMessage())));
	}

}
