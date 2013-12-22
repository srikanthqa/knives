package com.github.knives.script
/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Original code is taken from
 * http://groovy.codehaus.org/WrappingGroovyScript
 * 
 * Wrap a script and groovy jars to an executable jar
 * along with all necessary dependencies
 * 
 * Example: 
 * 
 * # Package itself
 * groovy PackageGroovyScript.groovy -s PackageGroovyScript.groovy
 * 
 * TODO: integrate with @Grab
 * TODO: trim the jar so to make it less fat
 */

import org.codehaus.groovy.tools.FileSystemCompiler;

//--------------------------------------------------------------------------
final def GROOVY_HOME = new File( System.getenv('GROOVY_HOME') )
if (!GROOVY_HOME.canRead()) {
  println "Missing environment variable GROOVY_HOME: '${GROOVY_HOME}'"
  return
}

//--------------------------------------------------------------------------
final def cli = new CliBuilder(usage: 'PackageGroovyScript')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.s( longOpt: 'script', argName: 'script', required: true, args: 1, 'path to script i.e. /path/to/HelloWorld.groovy' )
cli.d( longOpt: 'destJar', argName: 'destJar', required: false, args: 1, 'jar destintation filename, /path/to/{script}.jar in same directory as script' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def scriptFileArgPath = opt.s
final def scriptFile = new File( scriptFileArgPath )
if (!scriptFile.canRead()) {
	println "Cannot read script file: '${scriptFileArgPath}'"
	return
}

final def scriptFileName = scriptFile.name
final def scriptName = scriptFileName.substring(0, scriptFileName.size() - '.groovy'.size())
final def scriptCanonicalPath = scriptFile.canonicalPath
final def scriptFilePath = scriptCanonicalPath.substring(0, scriptCanonicalPath.size() - scriptFileName.size()) 
final def jarFileName = scriptName + '.jar'
final def destFile = opt.d ? opt.d : scriptFilePath + jarFileName

//--------------------------------------------------------------------------

final def ant = new AntBuilder()
ant.echo( "Compiling ${scriptFile}" )

FileSystemCompiler.main( [ scriptFile ] as String[] )

ant.jar( destfile: destFile, compress: true, index: true ) {
	fileset( dir: scriptFilePath, includes: '*.class' )

	zipgroupfileset( dir: GROOVY_HOME, includes: 'embeddable/groovy-all-*.jar' )
	zipgroupfileset( dir: GROOVY_HOME, includes: 'lib/commons*.jar' )
	// add more jars here

	manifest {
		attribute( name: 'Main-Class', value: scriptName )
	}
}

ant.delete( verbose: true ) {
	fileset( dir: scriptFilePath, includes: '*.class' )
}

ant.echo("Run jar using: \'java -jar ${jarFileName}\'" )