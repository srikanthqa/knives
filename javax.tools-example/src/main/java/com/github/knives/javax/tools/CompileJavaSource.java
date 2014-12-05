package com.github.knives.javax.tools;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Copy and paste directly from: http://www.accordess.com/wpblog/an-overview-of-java-compilation-api-jsr-199/
 * For further modification
 * 
  * A test class to test dynamic compilation API.
  */
public class CompileJavaSource {
	final private static Logger logger = LoggerFactory.getLogger(CompileJavaSource.class) ;

	/**Java source code to be compiled dynamically*/
	static String sourceCode = "package com.accordess.ca;" +
	"class DynamicCompilationHelloWorld{" +
	"public static void main (String args[]){" +
	"System.out.println (\"Hello, dynamic compilation world!\");" +
	"}" +
	"}" ;
	/**
	      * Does the required object initialization and compilation.
	      */
	public void doCompilation (){
		/*Creating dynamic java source code file object*/
		SimpleJavaFileObject fileObject = new DynamicJavaSourceCodeObject ("com.accordess.ca.DynamicCompilationHelloWorld", sourceCode) ;
		JavaFileObject[] javaFileObjects = new JavaFileObject[] { fileObject };
		
		/*Instantiating the java compiler*/
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		/**
		          * Retrieving the standard file manager from compiler object, which is used to provide
		          * basic building block for customizing how a compiler reads and writes to files.
		          *
		          * The same file manager can be reopened for another compiler task.
		          * Thus we reduce the overhead of scanning through file system and jar files each time
		          */
		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
		/* Prepare a list of compilation units (java source code file objects) to input to compilation task*/
		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);

		/*Prepare any compilation options to be used during compilation*/
		//In this example, we are asking the compiler to place the output files under bin folder.
		String[] compileOptions = new String[] {"-d", "bin"};
		Iterable<String> compilationOptionss = Arrays.asList(compileOptions);

		/*Create a diagnostic controller, which holds the compilation problems*/
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();

		/*Create a compilation task from compiler by passing in the required input objects prepared above*/
		CompilationTask compilerTask = compiler.getTask(null, stdFileManager, diagnostics, compilationOptionss, null, compilationUnits) ;

		//Perform the compilation by calling the call method on compilerTask object.
		boolean status = compilerTask.call();

		if (!status){//If compilation error occurs
			/*Iterate through each compilation problem and print it*/
			for (Diagnostic diagnostic : diagnostics.getDiagnostics()){
				System.out.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic);
			}
		}
		try {
			stdFileManager.close() ;//Close the file manager
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new CompileJavaSource ().doCompilation() ;
	}

}

/**
  * Creates a dynamic source code file object
  *
  * This is an example of how we can prepare a dynamic java source code for compilation.
  * This class reads the java code from a string and prepares a JavaFileObject
  *
  */
class DynamicJavaSourceCodeObject extends SimpleJavaFileObject {
	private String qualifiedName ;
	private String sourceCode ;

	/**
	      * Converts the name to an URI, as that is the format expected by JavaFileObject
	      *
	      *
	      * @param fully qualified name given to the class file
	      * @param code the source code string
	      */
	protected DynamicJavaSourceCodeObject(String name, String code) {
		super(URI.create("string:///" +name.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
		this.qualifiedName = name ;
		this.sourceCode = code ;
	}

	@Override
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
		return sourceCode ;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}

	public void setQualifiedName(String qualifiedName) {
		this.qualifiedName = qualifiedName;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}
}