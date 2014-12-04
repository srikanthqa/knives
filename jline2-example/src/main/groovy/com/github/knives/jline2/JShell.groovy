package com.github.knives.jline2

import java.util.LinkedList;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

/**
 * Copy and paste the following example
 * https://raw.github.com/jline/jline2/master/src/test/java/jline/example/Example.java
 *
 * To create your own console
 */
class JShell {

	static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'JShell <none/simple/files> [trigger <trigger>] [mask <mask>]')
		cli.h( longOpt: 'help', required: false, 'show usage information' )
		cli.n( longOpt: 'none', required: false, '(default) no completers' )
		cli.s( longOpt: 'simple', required: false, 'a simple completer that completes "foo, bar, and baz"' )
		cli.f( longOpt: 'files', required: false, 'a completer that completes file names' )
		cli.c( longOpt: 'color', required: false, 'colored prompt and feedback' )
		cli.t( longOpt: 'trigger', required: false, args: 1, 'a special word which causes it to assume the next line is a password' )
		cli.m( longOpt: 'mask', required: false, args: 1, 'the character to print in place of the actual password character' )
		
		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h) {
			cli.usage();
			return
		}
		
		boolean color = false;
		
		final ConsoleReader reader = new ConsoleReader();
		reader.setPrompt("prompt> ");
		
		final String trigger = opt.t
		final Character mask = opt.m ? opt.m.charAt(0) : null
		
		final List<Completer> completors = new LinkedList<Completer>();
		
		if (opt.f) { completors.add(new FileNameCompleter()) }
		if (opt.s) { completors.add(new StringsCompleter("foo", "bar", "baz")) }
		if (opt.c) {
			color = true
			reader.setPrompt("\u001B[1mfoo\u001B[0m@bar\u001B[32m@baz\u001B[0m> ")
		}
		
		completors.each { reader.addCompleter(it) }
		
		String line
		PrintWriter out = new PrintWriter(reader.getOutput())
		
		while ((line = reader.readLine()) != null) {
			if (color){
				out.println("\u001B[33m======>\u001B[0m\"" + line + "\"");
			} else {
				out.println("======>\"" + line + "\"");
			}
			out.flush();
		
			// If we input the special word then we will mask
			// the next line.
			if ((trigger != null) && (line.compareTo(trigger) == 0)) {
				line = reader.readLine("password> ", mask);
			}
			if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
				break;
			}
		}
	}
}
