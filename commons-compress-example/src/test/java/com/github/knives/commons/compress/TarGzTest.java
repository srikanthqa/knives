package com.github.knives.commons.compress;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.junit.Test;

public class TarGzTest {

	@Test
	public void testCreate() throws Exception {
		File file = new File("/tmp/hello.tar.gz");
		FileOutputStream fileStream = new FileOutputStream(file);
		
		GzipCompressorOutputStream gzipOutputStream = new GzipCompressorOutputStream(fileStream);
		TarArchiveOutputStream tarOutputStream = new TarArchiveOutputStream(gzipOutputStream);
		TarArchiveEntry tarEntry = new TarArchiveEntry("world.txt");
		// require
		tarEntry.setSize(20);
		
		tarOutputStream.putArchiveEntry(tarEntry);
		tarOutputStream.write("compress and archive".getBytes(StandardCharsets.US_ASCII));
		tarOutputStream.closeArchiveEntry();
		tarOutputStream.flush();
		tarOutputStream.close();
	}
	
	@Test
	public void testCreateDirectory() throws Exception {
		File file = new File("/tmp/hello1.tar.gz");
		FileOutputStream fileStream = new FileOutputStream(file);
		
		GzipCompressorOutputStream gzipOutputStream = new GzipCompressorOutputStream(fileStream);
		TarArchiveOutputStream tarOutputStream = new TarArchiveOutputStream(gzipOutputStream);
		
		/*
		// Not required / needed
		TarArchiveEntry tarDirectoryEntry = new TarArchiveEntry("/hello");
		tarDirectoryEntry.setMode(TarArchiveEntry.DEFAULT_DIR_MODE);
		tarOutputStream.putArchiveEntry(tarDirectoryEntry);
		tarOutputStream.closeArchiveEntry();
		*/
		
		TarArchiveEntry tarEntry = new TarArchiveEntry("/hello/world.txt");
		tarEntry.setSize(20);
		
		tarOutputStream.putArchiveEntry(tarEntry);
		tarOutputStream.write("compress and archive".getBytes(StandardCharsets.US_ASCII));
		tarOutputStream.closeArchiveEntry();

		tarOutputStream.flush();
		tarOutputStream.close();
	}

}
