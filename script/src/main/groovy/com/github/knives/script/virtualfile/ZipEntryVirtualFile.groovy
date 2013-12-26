package com.github.knives.script.virtualfile

import groovy.transform.Canonical

import java.util.zip.ZipFile

import org.apache.commons.io.FilenameUtils

import com.github.knives.script.deep.DeepGrep;

@Canonical
class ZipEntryVirtualFile implements VirtualFile {
	private ZipFile zipFile
	private String entryName
	private String rootZipFilePath
	
	@Override
	public byte[] getBytes() {
		if (isContainer()) return new byte[0]
		return zipFile.getInputStream(zipFile.getEntry(entryName)).getBytes()
	}
	
	@Override
	public String getContent() {
		if (isContainer()) return ""
		return zipFile.getInputStream(zipFile.getEntry(entryName)).getText()
	}
	
	@Override
	public String getCanonicalPath() {
		return rootZipFilePath + '/' + entryName
	}
	
	@Override
	public String getBaseName() {
		return FilenameUtils.getBaseName(getCanonicalPath())
	}
	
	@Override
	public String getExtension() {
		return FilenameUtils.getExtension(getCanonicalPath()).toLowerCase()
	}
	
	@Override
	public boolean isContainer() {
		if (zipFile.getEntry(entryName).isDirectory()) return true
		return getExtension() in ZIP_EXTENSIONS
	}
	
	@Override
	public List<VirtualFile> getChildren() {
		if (isContainer() == false) return []
		if (zipFile.getEntry(entryName).isDirectory()) return []
		
		final def extractFile = File.createTempFile(DeepGrep.class.getName() + '-' + getBaseName(), getExtension())
		
		extractFile << zipFile.getInputStream(zipFile.getEntry(entryName))
		
		// remove the file afterward
		extractFile.deleteOnExit()
		
		final def zipFile = new ZipFile(extractFile)

		return VirtualFileFactory.createVirtualFile(zipFile, getCanonicalPath())
	}

	// TODO: default implementation for now
	@Override
	public int getChildrenCount() {
		return getChildren().size();
	}
}
