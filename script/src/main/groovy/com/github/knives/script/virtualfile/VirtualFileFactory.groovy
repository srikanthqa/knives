package com.github.knives.script.virtualfile

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class VirtualFileFactory {
	public static VirtualFile createVirtualFile(String path) {
		final def file = new File(path)
		if (file.exists() == false) {
			return new NullVirtualFile(path: path)
		} else {
			return new RegularVirtualFile(file: file)
		}
		
		// TODO: recursive find virtual file for virtual path
	}
	
	public static List<VirtualFile> createVirtualFile(final ZipFile zipFile, final String rootZipFilePath) {
		final def virtualFiles = []
		
		zipFile.entries().each { final ZipEntry entry ->
			final String entryName = entry.getName()
			virtualFiles << new ZipEntryVirtualFile(zipFile: zipFile,
				entryName: entryName, rootZipFilePath: rootZipFilePath)
		}
		
		return virtualFiles
	}
}
