package com.github.knives.commons.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.FileUtils;
import org.junit.Ignore;
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

	@Test
	@Ignore
	public void testExtract() throws Exception {
		File file = new File("/tmp/hello.tar.gz");
		FileInputStream fileStream = new FileInputStream(file);
		
		GzipCompressorInputStream gzIn = new GzipCompressorInputStream(fileStream);
		
		TarArchiveInputStream archiveInputStream = new TarArchiveInputStream(gzIn);
		
		for (TarArchiveEntry archiveEntry = archiveInputStream.getNextTarEntry();
				archiveEntry != null; 
				archiveEntry = archiveInputStream.getNextTarEntry()) {
			
			System.out.println(archiveEntry.getName());
			
			if (archiveEntry.isFile()) {
				File extractFile = new File(archiveEntry.getName());
				file.getParentFile().mkdirs();
				FileUtils.copyInputStreamToFile(new LimitedInputStream(archiveInputStream), extractFile);	
			} 
		}
	}
	
	
	private static class LimitedInputStream extends FilterInputStream {
		protected LimitedInputStream(InputStream in) {
			super(in);
		}
		
		// commons-io using this method instead of read(b,offset,length)
		@Override
		public int read(byte[] b) throws IOException {
			return read(b, 0, b.length);
		}
		
		// close() so that commons-io won't close actual stream
		@Override
		public void close() { }
		
	}
}
