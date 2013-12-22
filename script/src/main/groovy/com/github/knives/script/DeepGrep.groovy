package com.github.knives.script
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
import org.objectweb.asm.Type
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.FieldNode
import org.objectweb.asm.tree.AnnotationNode

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
		if (virtualFile.getCanonicalPath().matches(keywords)) return true
		if (virtualFile.getBaseName().matches(keywords)) return true
		if (virtualFile.getExtension().equalsIgnoreCase(keywords)) return true
		return false
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
		println virtualFile.getCanonicalPath()
		final ClassReader reader = new ClassReader(virtualFile.getBytes())
		
		final ClassNode classNode = new ClassNode()
		reader.accept(classNode, 0)
		
		printClass(classNode)
		printFields(classNode)
		printMethods(classNode)
		printEndBracket()
		
		println()
	}
	
	static String BASE_OBJECT = "java.lang.Object"
	static String BASE_ANNOTATION = "java.lang.annotation.Annotation"
	static String INDENT = StringUtils.repeat(" ", 2)
	static String CONSTRUCTOR = "<init>"
	static String STATIC_INIT = "<clinit>"
	
	private String getClassName(String name) {
		return Type.getObjectType(name).getClassName()
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
		VARARGS("varargs"),
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
	
	static def CLASS_OPCODES = [:]
	static {
		CLASS_OPCODES[Opcodes.ACC_PUBLIC] = JavaModifier.PUBLIC
		CLASS_OPCODES[Opcodes.ACC_PRIVATE] = JavaModifier.PRIVATE
		CLASS_OPCODES[Opcodes.ACC_PROTECTED] = JavaModifier.PROTECTED
		CLASS_OPCODES[Opcodes.ACC_FINAL] = JavaModifier.FINAL
		CLASS_OPCODES[Opcodes.ACC_INTERFACE] = JavaModifier.INTERFACE
		CLASS_OPCODES[Opcodes.ACC_ABSTRACT] = JavaModifier.ABSTRACT
		CLASS_OPCODES[Opcodes.ACC_ANNOTATION] = JavaModifier.ANNOTATION
		CLASS_OPCODES[Opcodes.ACC_ENUM] = JavaModifier.ENUM
		CLASS_OPCODES[Opcodes.ACC_DEPRECATED] = JavaModifier.DEPRECATED
	}
	
	static def METHOD_OPCODES = [:]
	static {
		METHOD_OPCODES[Opcodes.ACC_PUBLIC] = JavaModifier.PUBLIC
		METHOD_OPCODES[Opcodes.ACC_PRIVATE] = JavaModifier.PRIVATE
		METHOD_OPCODES[Opcodes.ACC_PROTECTED] = JavaModifier.PROTECTED
		METHOD_OPCODES[Opcodes.ACC_STATIC] = JavaModifier.STATIC
		METHOD_OPCODES[Opcodes.ACC_FINAL] = JavaModifier.FINAL
		METHOD_OPCODES[Opcodes.ACC_SYNCHRONIZED] = JavaModifier.SYNCHRONIZED
		METHOD_OPCODES[Opcodes.ACC_BRIDGE] = JavaModifier.BRIDGE
		METHOD_OPCODES[Opcodes.ACC_VARARGS] = JavaModifier.VARARGS
		METHOD_OPCODES[Opcodes.ACC_NATIVE] = JavaModifier.NATIVE
		METHOD_OPCODES[Opcodes.ACC_ABSTRACT] = JavaModifier.ABSTRACT
	}
	
	static def FIELD_OPCODES = [:]
	static {
		FIELD_OPCODES[Opcodes.ACC_PUBLIC] = JavaModifier.PUBLIC
		FIELD_OPCODES[Opcodes.ACC_PRIVATE] = JavaModifier.PRIVATE
		FIELD_OPCODES[Opcodes.ACC_PROTECTED] = JavaModifier.PROTECTED
		FIELD_OPCODES[Opcodes.ACC_STATIC] = JavaModifier.STATIC
		FIELD_OPCODES[Opcodes.ACC_FINAL] = JavaModifier.FINAL
		FIELD_OPCODES[Opcodes.ACC_VOLATILE] = JavaModifier.VOLATILE
		FIELD_OPCODES[Opcodes.ACC_TRANSIENT] = JavaModifier.TRANSIENT
		FIELD_OPCODES[Opcodes.ACC_SYNTHETIC] = JavaModifier.SYNTHETIC
	}
	
	private List<JavaModifier> translateAccessToModifiers(def opcodesMap, int access) {
		return (translateAccessToModifiers(opcodesMap, access) { it })
	}
	
	private List<JavaModifier> translateAccessToModifiers(def opcodesMap, int access, Closure tweak) {
		def modifiers = []
		
		opcodesMap.each { opcode, modifier ->
			if ((access & opcode) != 0) {
				modifiers << modifier
			}
		}
		
		return tweak.call(modifiers)
	}
	
	private List<JavaModifier> translateClassAccessToModifiers(int access) {
		return translateAccessToModifiers(CLASS_OPCODES, access) { List<JavaModifier> modifiers ->
			
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
	
	private List<JavaModifier> translateMethodAccessToModifiers(int access) {
		return translateAccessToModifiers(METHOD_OPCODES, access)
	}
	
	private List<JavaModifier> translateFieldAccessToModifier(int access) {
		return translateAccessToModifiers(FIELD_OPCODES, access)
	}

	private void printClass(final ClassNode classNode) {
		final String name = classNode.name
		final int access = classNode.access
		final String superName = classNode.superName
		final List<String> interfaces = classNode.interfaces
		final def modifiers = translateClassAccessToModifiers(access)
		final boolean isAnnotation = (JavaModifier.ANNOTATION in modifiers)
		final String classModifiers = (modifiers*.getValue().join(" "))
		
		print "${classModifiers} ${getClassName(name)}"
		
		if (StringUtils.isBlank(superName) == false) {
			final String fullyQualifiedSuperName = getClassName(superName)
			if (BASE_OBJECT != fullyQualifiedSuperName) {
				print " extends ${fullyQualifiedSuperName}"
			}
		}
		
		if (isAnnotation == false && interfaces != null && interfaces.size() > 0) {
			print " implements "
			print ((interfaces.collect {  getClassName(it) } ).join(", "))
		}
		
		println " {"
	}
	
	private void printFields(final ClassNode classNode) {
		classNode.fields.each { final FieldNode field ->
			final String name = field.name
			final int access = field.access
			final String desc = field.desc
			final def modifiers = translateFieldAccessToModifier(access)
			final Type type = Type.getType(desc)
			final String fieldModifiers = (modifiers*.getValue().join(" "))
			
			print INDENT
			print fieldModifiers
			if (StringUtils.isBlank(fieldModifiers) == false) print " "
			println "${type.getClassName()} ${name};" 
			println()
		}
	}

	private void printMethods(final ClassNode classNode) {
		final def isInterface = JavaModifier.INTERFACE in translateClassAccessToModifiers(classNode.access)
		final String className = classNode.name.split('/').last()
		
		classNode.methods.each { final MethodNode method ->
			final String name = method.name == CONSTRUCTOR ? className : method.name
			
			if (STATIC_INIT == name) {
				// skip inline static initialization
				return
			}
			
			final int access = method.access
			final List<String> exceptions = method.exceptions
			final def modifiers = translateMethodAccessToModifiers(access)
			final String desc = method.desc
			final Type[] argTypes = Type.getArgumentTypes(desc)
			final Type returnType = Type.getReturnType(desc)
			
			if (isInterface) {
				modifiers.remove(JavaModifier.ABSTRACT)
			}
			
			final String methodModifiers = (modifiers*.getValue().join(" "))
			
			print INDENT
			print methodModifiers
			if (StringUtils.isBlank(methodModifiers) == false) print " "
			print "${returnType.getClassName()} ${name}("
			print argTypes*.getClassName().join(", ")
			print ")"
			
			if (exceptions != null && exceptions.isEmpty() == false) {
				print " throws "
				print ((exceptions.collect {  getClassName(it) } ).join(", "))
			}
			
			println ";"
			println()
		}
	}

	public void printEndBracket() {
		println "}"
	}
	
	public void printAnnotations(List<AnnotationNode> annotations, String indent) {
		annotations.each { final AnnotationNode anotation ->
			println indent
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