@Grapes([
	@Grab(group='commons-io', module='commons-io', version='2.4'),
	@Grab(group='org.ow2.asm', module='asm', version='4.2'),
	@Grab(group='org.ow2.asm', module='asm-util', version='4.2'),
	@Grab(group='org.apache.commons', module='commons-lang3', version='3.1')
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
import org.apache.commons.lang3.StringUtils

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
	
	private void printNonClassDetail(final VirtualFile virtualFile) {
		println virtualFile.getCanonicalPath()
	}

	private void printClassDetail(final VirtualFile virtualFile) {
		final ClassReader reader = new ClassReader(virtualFile.getBytes())
		final ClassPrinter classPrinter = new ClassPrinter();
		reader.accept(classPrinter, 0);
	}

	static class ClassPrinter extends ClassVisitor {
		
		static String BASE_OBJECT = "java.lang.Object"
		static String BASE_ANNOTATION = "java.lang.annotation.Annotation"
		
		public ClassPrinter() {
			super(Opcodes.ASM4);
		}
		
		public void visit(int version, int access, String name,
				String signature, String superName, String[] interfaces) {
				
			final def modifiers = translateClassAccessToModifiers(access)
			final boolean isAnnotation = (JavaModifier.ANNOTATION in modifiers)
			
			print (modifiers*.getValue().join(" "))
			print " "
			print normalizeFullQualifiedClassName(name)
			
			if (StringUtils.isBlank(superName) == false) {
				final String fullyQualifiedSuperName = normalizeFullQualifiedClassName(superName)
				if (BASE_OBJECT != fullyQualifiedSuperName) {
					print " extends ${fullyQualifiedSuperName}"
				}
			}
			
			if (isAnnotation == false && interfaces != null && interfaces.size() > 0) {
				print " implements "
				print ((interfaces.collect {  normalizeFullQualifiedClassName(it) } ).join(", "))
			}
			
			println " {"
		}
				
		public void visitSource(String source, String debug) { }
		
		public void visitOuterClass(String owner, String name, String desc) { }
		
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) { return null }
		
		public void visitAttribute(Attribute attr) { }
		
		public void visitInnerClass(String name, String outerName,
				String innerName, int access) { }
			
		public FieldVisitor visitField(int access, String name, String desc,
				String signature, Object value) {
			
			// TODO: 
			return null;
		}
				
		public MethodVisitor visitMethod(int access, String name,
				String desc, String signature, String[] exceptions) {
				
			// TODO: 
				
			return null;
		}
				
		public void visitEnd() {
			println "}"
		}
		
		
		private String normalizeFullQualifiedClassName(String name) {
			return name.replace('/', '.')
		}
		
		static enum JavaModifier {
			PUBLIC("public"),
			PRIVATE("private"),
			PROTECTED("protected"),
			STATIC("static"),
			FINAL("final"),
			SUPER("super"),
			SYNCHRONIZED("synchronized"),
			VOLATILE("volatile"),
			BRIDGE("bridge"),
			VARAGS("varags"),
			TRANSIENT("transient"),
			NATIVE("native"),
			INTERFACE("interface"),
			ABSTRACT("abstract"),
			SYNTHETIC("synthetic"),
			STRICT("strict"),
			ANNOTATION("@interface"),
			ENUM("enum"),
			DEPRECATED("@Deprecated"),
			CLASS("class");
			
			final String value
			
			private JavaModifier(String value) {
				this.value = value
			}
			
			public getValue() { return value }
		}
		
		static def MODIFIERS = [:]
		static {
			MODIFIERS[Opcodes.ACC_PUBLIC] = JavaModifier.PUBLIC
			MODIFIERS[Opcodes.ACC_PRIVATE] = JavaModifier.PRIVATE
			MODIFIERS[Opcodes.ACC_PROTECTED] = JavaModifier.PROTECTED
			MODIFIERS[Opcodes.ACC_STATIC] = JavaModifier.STATIC
			MODIFIERS[Opcodes.ACC_FINAL] = JavaModifier.FINAL
			MODIFIERS[Opcodes.ACC_SUPER] = JavaModifier.SUPER
			MODIFIERS[Opcodes.ACC_SYNCHRONIZED] = JavaModifier.SYNCHRONIZED
			MODIFIERS[Opcodes.ACC_VOLATILE] = JavaModifier.VOLATILE
			MODIFIERS[Opcodes.ACC_BRIDGE] = JavaModifier.BRIDGE
			MODIFIERS[Opcodes.ACC_VARARGS] = JavaModifier.VARAGS
			MODIFIERS[Opcodes.ACC_TRANSIENT] = JavaModifier.TRANSIENT
			MODIFIERS[Opcodes.ACC_NATIVE] = JavaModifier.NATIVE
			MODIFIERS[Opcodes.ACC_INTERFACE] = JavaModifier.INTERFACE
			MODIFIERS[Opcodes.ACC_ABSTRACT] = JavaModifier.ABSTRACT
			MODIFIERS[Opcodes.ACC_SYNTHETIC] = JavaModifier.SYNTHETIC
			MODIFIERS[Opcodes.ACC_STRICT] = JavaModifier.STRICT
			MODIFIERS[Opcodes.ACC_ANNOTATION] = JavaModifier.ANNOTATION
			MODIFIERS[Opcodes.ACC_ENUM] = JavaModifier.ENUM
			MODIFIERS[Opcodes.ACC_DEPRECATED] = JavaModifier.DEPRECATED
		}
		
		static def CLASS_OPCODES = [
			Opcodes.ACC_PUBLIC,
			Opcodes.ACC_PRIVATE,
			Opcodes.ACC_PROTECTED,
			Opcodes.ACC_FINAL,
			Opcodes.ACC_INTERFACE,
			Opcodes.ACC_ABSTRACT,
			Opcodes.ACC_ANNOTATION,
			Opcodes.ACC_ENUM,
			Opcodes.ACC_DEPRECATED,
		]
		
		static def METHOD_OPCODES = [
			Opcodes.ACC_PUBLIC,
			Opcodes.ACC_PRIVATE,
			Opcodes.ACC_PROTECTED,
			Opcodes.ACC_STATIC,
			Opcodes.ACC_FINAL,
			Opcodes.ACC_SYNCHRONIZED,
			Opcodes.ACC_BRIDGE,
			Opcodes.ACC_VARARGS,
			Opcodes.ACC_NATIVE,
			Opcodes.ACC_ABSTRACT
		]
		
		static def FIELD_OPCODES = [
			Opcodes.ACC_PUBLIC,
			Opcodes.ACC_PRIVATE,
			Opcodes.ACC_PROTECTED,
			Opcodes.ACC_STATIC,
			Opcodes.ACC_FINAL,
			Opcodes.ACC_VOLATILE,
			Opcodes.ACC_TRANSIENT,
			Opcodes.ACC_SYNTHETIC
		]
		
		private List<JavaModifier> translateClassAccessToModifiers(int classAccess) {
			def modifiers = []
			
			CLASS_OPCODES.each { opcode -> 
				if ((classAccess & opcode) != 0) { 
					modifiers << MODIFIERS[opcode]
				}
			}
			
			
			
			// tweak, interface is actually an abstract class
			if (JavaModifier.INTERFACE in modifiers) {
				modifiers.remove(JavaModifier.ABSTRACT)
			}
			
			def defaultType = [JavaModifier.INTERFACE, JavaModifier.ENUM, JavaModifier.ANNOTATION]
			
			// tweak, there is no class in Opcodes, because it is by default
			// everything is a class
			if (modifiers.intersect(defaultType).isEmpty()) {
				modifiers << JavaModifier.CLASS
			}
			
			// tweak, both annotation and interface show up, so we have to remove
			// interface when have annotation
			if (JavaModifier.ANNOTATION in modifiers) {
				modifiers.remove(JavaModifier.INTERFACE)
			}
			
			return modifiers
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