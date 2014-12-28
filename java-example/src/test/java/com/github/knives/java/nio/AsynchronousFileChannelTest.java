package com.github.knives.java.nio;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AsynchronousFileChannelTest {
	final private static Logger LOG = LoggerFactory.getLogger(AsynchronousFileChannelTest.class);

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	/**
	 * Why there is no AsynchronousChannelGroup for AsynchronousFileChannel.open
	 * 
	 * http://mail.openjdk.java.net/pipermail/nio-dev/2011-December/001520.html
	 */
	@Test
	public void test() throws Exception {
		Path path = Paths.get(createNonEmptyFile());
		
		try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ, StandardOpenOption.WRITE)) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(50);
			Future<Integer> result = fileChannel.read(byteBuffer, 0);
			int pos = result.get();
			LOG.info("Read [{}] bytes", pos);
			
			LOG.info("Read [{}]", new String(byteBuffer.array(), 0, pos, StandardCharsets.UTF_8));
		}
	}

	
	private String createNonEmptyFile() throws IOException {
		File tmpFile = tempFolder.newFile();
		FileUtils.writeByteArrayToFile(tmpFile, "hello world".getBytes(StandardCharsets.UTF_8));
		return tmpFile.getAbsolutePath();
	}
}
