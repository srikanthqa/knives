package com.github.knives.build

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY
import groovy.transform.Immutable

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import java.nio.file.attribute.PosixFilePermission
import java.nio.file.attribute.PosixFilePermissions
import java.util.concurrent.TimeUnit

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class AbstractStartExternalProcess extends DefaultTask {
	
	@TaskAction
	void run() {
		
		startProcess()
		
		final File taskTempDirectory = getTemporaryDir()
		def watchThread = new Thread(new WatchStopFile(taskTempDirectory, "stopFile", this));
		watchThread.setDaemon(true)
		watchThread.start()
	}
	
	protected void startProcess() {
		
	}
	
	public void stopProcess() {
		
	}
	
	@Immutable
	class WatchStopFile implements Runnable {
		final private File directory
		final private String stopFileName
		final private AbstractStartExternalProcess task
		
		@Override
		public void run() {
			watchStopFile()
			
			task.stopProcess()
		}
		
	 	private void watchStopFile() {
			 Path directoryPath = Paths.getAt(directory.getCanonicalPath())
			 Path filePath = Paths.getAt(directory.getCanonicalPath(), stopFileName)
			 
			 Files.deleteIfExists(filePath)
			 try {
				 Files.createFile(filePath,
					 PosixFilePermissions.asFileAttribute(PosixFilePermission.values() as Set<PosixFilePermission>))
			 } catch (IOException ignore) {}
			 
			 WatchService watchService = FileSystems.getDefault().newWatchService()
			 
			 directoryPath.register(watchService, ENTRY_DELETE);
			 
			 while (true) {
				 try {
					 // mix of poll and event driven to prevent race condition
					 if (!Files.exists(filePath)) return
					 
					 WatchKey watchKey = watchService.poll(60, TimeUnit.SECONDS)
					 List<WatchEvent<?>> events = watchKey.pollEvents()
					 for (WatchEvent<?> event : events) {
						 logger.debug("Event=[{}], count=[{}], context=[{}]", event.kind(), event.count(), event.context());
						 
						 if (event.kind().equals(StandardWatchEventKinds.ENTRY_DELETE)) {
							 if (event.context().equals(stopFileName) {
								 return
							 }
						 }
					 }
					 
					 if (!watchKey.reset()) { }
				 } catch (InterruptedException e) {
					 return
				 }
			 }
		}
	}
}
