package com.github.knives.groovy.closure
import groovy.io.FileType
import groovy.transform.InheritConstructors

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

import org.junit.Ignore
import org.junit.Test

/**
 * ZipBuilder utils tool
 * 
 * http://skepticalhumorist.blogspot.com/2011/01/groovy-dslbuilders-zip-output-streams.html
 */
public class ZipBuilderTest {

	@InheritConstructors
	static private class NonClosingOutputStream extends FilterOutputStream {
		@Override
		public void close() {
			// do nothing
		}
	}

	static class ZipBuilder {
		private final ZipOutputStream zos
		
		public ZipBuilder(OutputStream os) {
			zos = new ZipOutputStream(os)
		}
	
		void zip(Closure closure) {
			try {
				closure.delegate = this
				closure.call()
			} finally {
				zos.close()
			}
		}
	
		void entry(Map props, String name, Closure closure) {
			final def entry = new ZipEntry(name)
			props.each {k, v -> entry[k] = v}
			zos.putNextEntry(entry)
			final def ncos = new NonClosingOutputStream(zos)
			closure.call(ncos)
		}
	
		void entry(String name, Closure closure) {
			entry([:], name, closure)
		}
	}


	@Ignore
	@Test
	public void main() {
		def name = opt.n
		def paths = opt.arguments()
		
		new ZipBuilder(new FileOutputStream(new File(name))).zip {
			paths.each { final def path ->
				def pathFile = new File(path)
				
				if (pathFile.isDirectory()) {
					pathFile.traverse(type: FileType.FILES) { File file ->
						entry(file.path, size: file.length(), time: file.lastModified()) {it << file.bytes}
					}
				} else {
					entry(pathFile.path, size: pathFile.length(), time: pathFile.lastModified()) {it << pathFile.bytes}
				}
			}
		}
	}
}