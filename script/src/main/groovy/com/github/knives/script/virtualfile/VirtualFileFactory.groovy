package com.github.knives.script.virtualfile


class VirtualFileFactory {
	public VirtualFile createVirtualFile(String path) {
		final def file = new File(path)
		if (file.exists() == false) {
			return new NullVirtualFile(path: path)
		} else {
			return new RegularVirtualFile(file: file)
		}
		
		// TODO: recursive find virtual file for virtual path
	}
}
