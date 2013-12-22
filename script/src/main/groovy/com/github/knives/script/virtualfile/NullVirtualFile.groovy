package com.github.knives.script.virtualfile

import groovy.transform.Canonical

import org.apache.commons.io.FilenameUtils

@Canonical
class NullVirtualFile implements VirtualFile {

	private String path

	@Override
	public byte[] getBytes() {
		return new byte[0]
	}
	public String getContent() {
		return ""
	}

	@Override
	public String getCanonicalPath() {
		return path
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
		return false
	}

	@Override
	public List<VirtualFile> getChildren() {
		return []
	}
}
