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

public class DeepZipSearch {
	final private static def ZIP_EXTENSION = 'zip'
	final private static def JAR_EXTENSIONS = ['jar', 'war', 'ear']
	final private static def ZIP_EXTENSIONS = [
		ZIP_EXTENSION,
		JAR_EXTENSIONS
	].flatten()
	final private static def CLASS_EXTENSION = 'class'

	final def paths
	final def name

	public DeepZipSearch(def paths, def name) {
		this.paths = paths
		this.name = name
	}

	public void printSearchDetails() {
		paths.each { handleRawPath(it) }
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
		} else if (baseName == name) {
			if (extension == CLASS_EXTENSION) {
				printClassDetail(regularFile)
			} else {
				printNonClassDetail(regularFile)
			}
		}
	}

	private void handleZipFile(final File file) {
		def zipFile = new ZipFile(file)
		zipFile.entries().each { final ZipEntry entry ->
		}
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

		def name = opt.n
		def paths = opt.arguments()
		new DeepZipSearch(paths, name).printSearchDetails()
	}
}