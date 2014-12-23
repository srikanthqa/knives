package com.github.knives.commons.compress;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ZipTest {

	@Rule
	public TemporaryFolder tmpDir = new TemporaryFolder();
	
	@Test
	public void testCreateWithFile() throws Exception {
		File helloTxt = tmpDir.newFile("hello.txt");
		FileUtils.write(helloTxt, "hello world");
		
		ZipArchiveOutputStream zipOutputStream = new ZipArchiveOutputStream(new File("/tmp/hello.zip"));
		ZipArchiveEntry zipEntry = new ZipArchiveEntry(helloTxt, "world.txt");
		zipOutputStream.putArchiveEntry(zipEntry);
		// content of zip is written by write() rather than using constructor of ZipArchiveEntry
		zipOutputStream.write("hello stupid human".getBytes(StandardCharsets.US_ASCII));
		zipOutputStream.closeArchiveEntry();
		zipOutputStream.flush();
		zipOutputStream.close();
	}
	
	@Test
	public void testCreate() throws Exception {
		ZipArchiveOutputStream zipOutputStream = new ZipArchiveOutputStream(new File("/tmp/hello1.zip"));
		ZipArchiveEntry zipEntry = new ZipArchiveEntry("world1.txt");
		zipOutputStream.putArchiveEntry(zipEntry);
		zipOutputStream.write("hello stupid human".getBytes(StandardCharsets.US_ASCII));
		zipOutputStream.closeArchiveEntry();
		zipOutputStream.flush();
		zipOutputStream.close();
	}
	
	@Test
	public void testCreateDirectory() throws Exception {
		ZipArchiveOutputStream zipOutputStream = new ZipArchiveOutputStream(new File("/tmp/hello2.zip"));
		ZipArchiveEntry zipEntry = new ZipArchiveEntry("/hello/world1.txt");
		zipOutputStream.putArchiveEntry(zipEntry);
		zipOutputStream.write("hello stupid human".getBytes(StandardCharsets.US_ASCII));
		zipOutputStream.closeArchiveEntry();
		zipOutputStream.flush();
		zipOutputStream.close();
	}

}
