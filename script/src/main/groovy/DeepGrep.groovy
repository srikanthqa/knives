@Grapes([
	@Grab(group='commons-io', module='commons-io', version='2.4'),
	@Grab(group='org.ow2.asm', module='asm', version='4.2')
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
import groovy.transform.Canonical
import java.lang.annotation.Documented
import java.lang.annotation.Target
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

public class DeepGrep {
	final private static def ZIP_EXTENSIONS = ['zip', 'jar', 'war', 'ear', 'sar']
	final private static def CLASS_EXTENSION = 'class'

	final private def paths
	final private def keywords
	final private VirtualFileFactory virtualFileFactory

	public DeepGrep(def paths, def keywords) {
		this.paths = paths
		this.keywords = keywords
		this.virtualFileFactory = new VirtualFileFactory()
	}

	public void printSearchDetails() {
		paths.each { handleRawPath(it) }
	}
	
	public boolean match(final VirtualFile virtualFile) {
		// TODO: implement matching function here
		return true
	}

	public void print(final VirtualFile virtualFile) {
		if (virtualFile.getExtension() == CLASS_EXTENSION) {
			printClassDetail(virtualFile)
		} else {
			printNonClassDetail(virtualFile)
		}
	}
	
	private void handleRawPath(final String path) {
		final VirtualFile initialVirtualFile = virtualFileFactory.createVirtualFile(path)
		final LinkedList<VirtualFile> toBeProcessedVirtualFiles = new LinkedList<VirtualFile>()
		toBeProcessedVirtualFiles.push(initialVirtualFile)
		
		while (toBeProcessedVirtualFiles.isEmpty() == false) {
			final VirtualFile processedVirtualFile = toBeProcessedVirtualFiles.removeFirst()
			
			if (processedVirtualFile.isContainer()) {
				toBeProcessedVirtualFiles.addAll(processedVirtualFile.getChildren())
			} else {
				if (match(processedVirtualFile)) {
					print(processedVirtualFile)
				}
			}
		}
	}
	
	private void printNonClassDetail(final VirtualFile virtualFile) {
		println virtualFile.getCanonicalPath()
	}

	private void printClassDetail(final VirtualFile virtualFile) {
		final ClassReader reader = new ClassReader(virtualFile.getBytes())
		final ClassPrinter classPrinter = new ClassPrinter();
		reader.accept(classPrinter, 0);
	}

	// TODO: pretty print class detail
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
				return DeepGrep.createVirtualFile(zipFile, rootZipFilePath)
			}
		}
	}
	
	public static def createVirtualFile(final ZipFile zipFile, final String rootZipFilePath) {
		final def virtualFiles = []
		
		zipFile.entries().each { final ZipEntry entry ->
			final String entryName = entry.getName()
			virtualFiles << new ZipEntryVirtualFile(zipFile: zipFile,
				entryName: entryName, rootZipFilePath: rootZipFilePath)
		}
		
		return virtualFiles
	}
	
	
	@Canonical
	public static class ZipEntryVirtualFile implements VirtualFile {
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

			return DeepGrep.createVirtualFile(zipFile, getCanonicalPath())
		}
	}
	
	@Canonical
	public static class NullVirtualFile implements VirtualFile {
		private String path
		
		@Override
		public byte[] getBytes() { return new byte[0] }
		public String getContent() { return "" }
		
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
		public boolean isContainer() { return false }
		
		@Override
		public List<VirtualFile> getChildren() { return [] }
	}
	
	public static class VirtualFileFactory {
		public VirtualFile createVirtualFile(String path) {
			final def file = new File(path)
			if (file.exists() == false) {
				return new NullVirtualFile(path: path)
			} else {
				return new RegularVirtualFile(file: file)
			}
		}
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'DeepGrep -n <name> [path/to/jars|directory|file]')
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
		new DeepGrep(paths, keywords).printSearchDetails()
	}
}