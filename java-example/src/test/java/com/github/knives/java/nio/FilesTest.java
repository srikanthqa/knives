package com.github.knives.java.nio;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilesTest {

	final private static Logger LOG = LoggerFactory.getLogger(FilesTest.class);
	
	@Rule
	public TemporaryFolder tempDirectory = new TemporaryFolder();
	
	@Test
	public void testCopy() throws IOException {
		Path path = Paths.get(tempDirectory.getRoot().getCanonicalPath(), "test");
		
		Files.copy(new ByteArrayInputStream("hello world".getBytes(StandardCharsets.US_ASCII)), 
				path, StandardCopyOption.REPLACE_EXISTING);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		Files.copy(path, outputStream);
		
		assertEquals("hello world", outputStream.toString(StandardCharsets.US_ASCII.name()));
	}
	
	@Test
	public void testWalkFileTree() throws IOException {
		Files.walkFileTree(Paths.get("/tmp"), new FileVisitor<Path>() {

			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				LOG.info("dir=[{}], attrs=[{}]", dir, attrs);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				LOG.info("file=[{}], attrs=[{}]", file, attrs);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc)
					throws IOException {
				LOG.info("file=[{}], exc=[{}]", file, Optional.ofNullable(exc).map(IOException::toString).orElse(null));
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc)
					throws IOException {
				LOG.info("dir=[{}], exc=[{}]", dir, Optional.ofNullable(exc).map(IOException::toString).orElse(null));
				return FileVisitResult.CONTINUE;
			}

		});
	}

}
