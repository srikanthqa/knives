package com.github.knives.script.virtualfile

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.apache.commons.io.FilenameUtils

class VirtualFileFactory {
	public static VirtualFile createVirtualFile(final String path) {
		final def fragments = fragmentize(path)
		final def file = new File(fragments.get(0))
		
		if (file.exists() == false) {
			return new NullVirtualFile(path: path)
		} 
		
		VirtualFile virtualFile = new RegularVirtualFile(file: file)
		
		final StringBuilder builder = new StringBuilder(path.size())
		builder.append(virtualFile.getCanonicalPath())
		
		for (int i = 1; i < fragments.size(); i++) {
			builder.append(fragments.get(i))
			
			def matchedVirtualFiles = virtualFile.getChildren().grep { 
				builder.toString() == it.getCanonicalPath() 
			}
			
			if (matchedVirtualFiles.isEmpty()) {
				return new NullVirtualFile(path: path)
			}
			
			virtualFile = matchedVirtualFiles.first()
		}
		
		return virtualFile
	}
	
	// TODO: assuming linux separator for now
	// TODO: handle relative path
	// Always return at least one elements
	private static List<String> fragmentize(String path) {
		final def tokens = []
		
		while (path.isEmpty() == false) {
			final String parent = FilenameUtils.getFullPathNoEndSeparator(path)
			final String currentName = FilenameUtils.getName(path)
			if (parent == path) {
				tokens.push(parent)
				break
			} else {
				tokens.push(currentName)
				path = parent
			}
		}
		
		tokens.reverse(true)
		
		final def fragments = []
		
		def lastFragment = tokens.inject('') { String parent, String filename ->
			final String ext = FilenameUtils.getExtension(filename)
			
			// little hack
			final String trueParent = parent == '' && fragments.size() > 0 ? '/' : parent
			final String fullpath = FilenameUtils.concat(trueParent, filename)
			if (ext in VirtualFile.ZIP_EXTENSIONS) {
				fragments.push(fullpath)
				return ''	
			} else {
				return fullpath
			}
		}
		
		if (lastFragment != '') {
			fragments.push(lastFragment)
		} 
		
		return fragments
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
