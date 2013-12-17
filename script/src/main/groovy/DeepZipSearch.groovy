@Grapes([
	@Grab(group='commons-io', module='commons-io', version='2.4'),
	@Grab(group='org.ow2.asm', module='asm', version='4.2'),
	@Grab(group='com.google.guava', module='guava', version='15.0')
])

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import org.apache.commons.io.FilenameUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.Attribute
import org.objectweb.asm.FieldVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import com.google.common.collect.ImmutableList
import groovy.transform.Canonical


public class DeepZipSearch {
	final private static def ZIP_EXTENSIONS = ['zip', 'jar', 'war', 'ear', 'sar']
	final private static def CLASS_EXTENSION = 'class'

	final private def paths
	final private def keywords
	final private File tmpDir

	public DeepZipSearch(def paths, def keywords) {
		this.paths = paths
		this.keywords = keywords
	}

	public void printSearchDetails() {
		paths.each { handleRawPath(it) }
	}
	
	public void match(final VirtualFile virtualFile) {
		// TODO: implement matching function here
	}

	private void handleRawPath(String path) {
		final def pathFile = new File(path)
		if (pathFile.exists()) {
			if (pathFile.isDirectory()) {
				handleFileDirectory(pathFile)
			} else {
				handleRegularFile(pathFile)
			}
		} else {
			println "$path does not exists"
		}
	}

	private void handleFileDirectory(final File directory) {
		directory.eachFile { handleRawPath(it.canonicalPath) }
	}

	private void handleRegularFile(final File regularFile) {
		final def canonicalPath = regularFile.canonicalPath
		final def extension = FilenameUtils.getExtension(canonicalPath).toLowerCase()
		final def baseName = FilenameUtils.getBaseName(canonicalPath)

		if (extension in ZIP_EXTENSIONS) {
			handleZipFile(regularFile)
		} else if (baseName == keywords) {
			if (extension == CLASS_EXTENSION) {
				printClassDetail(regularFile)
			} else {
				printNonClassDetail(regularFile)
			}
		}
	}

	
	private void handleZipFile(final File file) {
		def zipFile = new ZipFile(file)
		
		final LinkedList<String> extractQueue = new  LinkedList<String>();
		
		zipFile.entries().each { final ZipEntry entry ->
			if (entry.isDirectory() == false) {
				final def canonicalPath = entry.getName()
				final def baseName = FilenameUtils.getBaseName(canonicalPath)
				final def extension = FilenameUtils.getExtension(canonicalPath).toLowerCase()
				
				println canonicalPath
				
				if (baseName == keywords) {
					
				}
				
				if (extension in ZIP_EXTENSIONS) {
					extractQueue.push(canonicalPath)
				}
			}
		}
		
		extractQueue.each { final String name -> 
			extractFileFromZipAndProcess(zipFile, name)
		}
	}
	
	private void extractFileFromZipAndProcess(final ZipFile zipFile, final String entryName) {
		println entryName
		//zipFile.getInputStream(zipFile.getEntry(entryName))
	}

	private void printNonClassDetail(final File nonClassFile) {
		println nonClassFile.canonicalPath
	}

	private void printClassDetail(final File classFile) {
		final ClassReader reader = new ClassReader(classFile.getBytes())
		final ClassPrinter classPrinter = new ClassPrinter();
		reader.accept(classPrinter, 0);
	}

	public class ClassPrinter extends ClassVisitor {
		public ClassPrinter() {
			super(Opcodes.ASM4);
		}
		public void visit(int version, int access, String name,
				String signature, String superName, String[] interfaces) {
			println "$name extends $superName  {"
		}
		public void visitSource(String source, String debug) {
			
		}
		
		public void visitOuterClass(String owner, String name, String desc) {
			
		}
		
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			return null;
		}
		
		public void visitAttribute(Attribute attr) {
			
		}
		
		public void visitInnerClass(String name, String outerName,
				String innerName, int access) {
				
		}
		public FieldVisitor visitField(int access, String name, String desc,
				String signature, Object value) {
				
			println " ${desc} ${name} "
			return null;
		}
		public MethodVisitor visitMethod(int access, String name,
				String desc, String signature, String[] exceptions) {
				
			println " $name$desc";
			
			return null;
		}
		public void visitEnd() {
			println "}"
		}
	}

	/**
	 * A simple abstraction over ZipEntry and RegularFile
	 * used for matching/reader function
	 */
	public static interface VirtualFile {
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
	}
	
	@Canonical
	public static class RegularVirtualFile implements VirtualFile {
		final private File file
		
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
			if (isContainer() == false) return ImmutableList.<VirtualFile>of()
			
		}
	}
	
	@Canonical
	public static class ZipEntryVirtualFile implements VirtualFile {
		final private ZipFile zipFile
		final private String entryName
		final private String rootZipFilePath
		
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
			if (isContainer() == false) return ImmutableList.<VirtualFile>of()
			
			// TODO: extract and create ZipEntryVirtualFile 
		}
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'DeepZipSearch -n <name> [path/to/jars|directory|file]')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		cli.n( longOpt: 'name', args: 1, required: true, 'search class by name')

		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage();
			return
		}

		def keywords = opt.n
		def paths = opt.arguments()
		new DeepZipSearch(paths, keywords).printSearchDetails()
	}
}