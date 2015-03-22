package com.github.knives.java.nio;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

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

public class WatchServiceTest {

	@Test
	public void testWatchServies() throws IOException {
		Path path = Paths.get("/tmp");
		WatchService watchService = FileSystems.getDefault().newWatchService();
		path.register(watchService, ENTRY_CREATE,
				ENTRY_DELETE, ENTRY_MODIFY);
		
		while (true) {
			try {
				WatchKey watchKey = watchService.poll(60, TimeUnit.SECONDS);
                if (watchKey == null) {
                    System.out.println("watchKey is null");
                    break;
                }
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent<?> event : events) {
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
