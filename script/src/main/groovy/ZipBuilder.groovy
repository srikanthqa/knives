import groovy.io.FileType
import groovy.transform.InheritConstructors

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * ZipBuilder utils tool
 * 
 * http://skepticalhumorist.blogspot.com/2011/01/groovy-dslbuilders-zip-output-streams.html
 */
public class ZipBuilder {

	@InheritConstructors
	static private class NonClosingOutputStream extends FilterOutputStream {
		@Override
		public void close() {
			// do nothing
		}
	}

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

	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'ZipBuilder -n <name> [path/to/directory|file]')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		cli.n( longOpt: 'name', args: 1, required: true, 'search class by name')
		
		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage();
			return
		}
		
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