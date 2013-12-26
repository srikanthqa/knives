package com.github.knives.script.virtualfile;

import java.util.List

/**
 * A simple abstraction over ZipEntry and RegularFile
 * used for matching/reader function
 */
public interface VirtualFile {
	final public static def ZIP_EXTENSIONS = ['zip', 'jar', 'war', 'ear', 'sar']

	// return content of virtual as byte
	// useless if VirtualFile is a container
	// return "" or empty for container virtual file
	public byte[] getBytes()
	public String getContent()
	
	// return full virtual path
	public String getCanonicalPath()
	public String getBaseName()
	public String getExtension()
	
	// return true if VirtualFile is a simply a container of other virtual file
	public boolean isContainer()
	
	// return the list of VirtualFile children of isContainer()
	// return empty if it is not
	public List<VirtualFile> getChildren()
	
	// return fast performance count for each concrete class of VirtualFile interface
	// at least better than getChildren().size(). 
	public int getChildrenCount()
}
