package com.github.knives.script.deep

import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileSystemManager

/**
 * DeepUtil meant to refactor common code between DeepGrep and DeepCompare
 */
class DeepUtil {
	
	static FileObject wrapFileObject(final FileSystemManager fsManager, final FileObject tmpFileObject) {
		if (fsManager.canCreateFileSystem(tmpFileObject)) {
			return fsManager.createFileSystem(tmpFileObject)
		} else {
			return tmpFileObject
		}
	}
}
