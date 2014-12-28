package com.github.knives.java.nio;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class PathsTest {

	@Test
	public void testTmpPath() {
		Path path = Paths.get("/tmp");
		assertEquals("/tmp", path.toString());
		assertEquals("/tmp", path.toAbsolutePath().toString());
	}

	@Test
	public void testCurrentPath() throws IOException {
		Path path = Paths.get(".");
		assertEquals(".", path.toString());
		assertEquals("", path.normalize().toString());
		System.out.println(path.toAbsolutePath());
		System.out.println(path.toRealPath());
	}

	@Test
	public void testDoNotExist() {
		Path path = Paths.get("/donotexist", "donotexist");
		assertEquals("/donotexist/donotexist", path.toString());
		assertEquals("/donotexist/donotexist", path.toAbsolutePath().toString());
	}

	@Test
	public void testParentPath() throws IOException {
		Path path = Paths.get("..");
		assertEquals("..", path.toString());
		assertEquals("..", path.normalize().toString());
		assertEquals("..", path.getFileName().toString());
		assertEquals(1, path.getNameCount());
		assertNull(path.getParent());
		assertFalse(path.isAbsolute());
		assertNull(path.getRoot());

		// call normalize after absolute path
		assertFalse(path.toAbsolutePath().normalize().toString().contains(".."));

		// absolute path throws java.io.IOError (fatal runtime exception)
		System.out.println(path.toAbsolutePath());

		// real path throws IOException
		System.out.println(path.toRealPath());

		path.toRealPath().spliterator()
				.forEachRemaining(path1 -> System.out.println(path1));
	}

	@Test
	public void testRelativizingAbsolutePath() {
		Path a = Paths.get("/a/b");
		Path b = Paths.get("/a/b/c/d");

		// a.relativize(b) = c ~ a - b = c => a + c = b

		assertEquals("c/d", a.relativize(b).toString());
		assertEquals("../..", b.relativize(a).toString());
	}

	/**
	 * cannot mix relativizing between absolute path and relative path
	 */
	@Test
	public void testRelativizingRelativePath() {
		Path a = Paths.get("a/b");
		Path b = Paths.get("a/b/c/d");

		// a.relativize(b) = c ~ a - b = c => a + c = b

		assertEquals("c/d", a.relativize(b).toString());
		assertEquals("../..", b.relativize(a).toString());
	}

	@Test
	public void testResolve() {
		assertEquals("/c", Paths.get("/a/b").resolve("/c").toString());
		assertEquals("/a/b/c", Paths.get("/a/b").resolve("c").toString());
		assertEquals("/c", Paths.get("a/b/").resolve("/c").toString());
		assertEquals("/a/c", Paths.get("/a/b").resolveSibling("c").toString());
	}

	@Test
	public void testWatchServies() throws IOException {
		Path path = Paths.get("/tmp");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		path.register(watchService, ENTRY_CREATE,
				ENTRY_DELETE, ENTRY_MODIFY);
		
		while (true) {
			try {
				WatchKey watchKey = watchService.poll(60, TimeUnit.SECONDS);
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent event : events) {
					System.out.println("Event " + event.kind() + ", count=" + event.count() + " " + event.context());
				}
				
				if (!watchKey.reset()) {
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}

}
