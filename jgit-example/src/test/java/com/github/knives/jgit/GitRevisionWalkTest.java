package com.github.knives.jgit;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevSort;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Test;

public class GitRevisionWalkTest {

	@Test
	public void test() throws IOException {
		final FileRepositoryBuilder builder = new FileRepositoryBuilder();

		final Repository repo = builder.readEnvironment()
				.findGitDir(new File("/tmp"))
				.build();
				
		final RevWalk revWalk = new RevWalk(repo);
		final ObjectId lastCommitId = repo.resolve(Constants.HEAD);
		final RevCommit lastCommit = revWalk.parseCommit(lastCommitId);


		revWalk.sort(RevSort.TOPO, true);
		revWalk.sort(RevSort.REVERSE, true);
		revWalk.markStart(lastCommit);

		revWalk.forEach(commit -> System.out.println(commit.getFullMessage()));
	}

}
