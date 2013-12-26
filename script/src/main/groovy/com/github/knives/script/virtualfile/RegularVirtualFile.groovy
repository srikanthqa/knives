package com.github.knives.script.virtualfile

import groovy.transform.Canonical

import java.util.zip.ZipFile

import org.apache.commons.io.FilenameUtils

import com.github.knives.script.deep.DeepGrep;

@Canonical
class RegularVirtualFile implements VirtualFile {

	private File file

	@Override
	public byte[] getBytes() {
		if (isContainer()) return new byte[0]
		return file.getBytes()
	}

	@Override
	public String getContent() {
		if (isContainer()) return ""
		return file.getText()
	}

	@Override
	public String getCanonicalPath() {
		return file.getCanonicalPath()
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
		if (file.isDirectory()) return true
		else return getExtension() in ZIP_EXTENSIONS
	}

	@Override
	public List<VirtualFile> getChildren() {
		if (isContainer() == false) return []

		if (file.isDirectory()) {
			final def children = []
			file.eachFile { children << new RegularVirtualFile(file: it) }
			return children
		} else {
			final def zipFile = new ZipFile(file)
			final String rootZipFilePath = file.getCanonicalPath()
			return VirtualFileFactory.createVirtualFile(zipFile, rootZipFilePath)
		}
	}

	// TODO: default implementation for now
	@Override
	public int getChildrenCount() {
		return getChildren().size();
	}
}
