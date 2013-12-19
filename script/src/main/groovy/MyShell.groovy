/**
 * Copy and paste the following example
 * https://raw.github.com/jline/jline2/master/src/test/java/jline/example/Example.java
 * 
 * To create your own console
 */

@Grapes([
	@Grab(group='jline', module='jline', version='2.11')
])

import java.util.LinkedList;
import java.util.List;
import jline.console.ConsoleReader;
import jline.console.completer.Completer;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

void usage() {
	println """Usage: java ${this.getClass().getName()} [none/simple/files/dictionary [trigger mask]]"
	none - no completers
	simple - a simple completer that completes "foo, bar, and baz"
	files - a completer that completes file names"
	classes - a completer that completes java class names
	trigger - a special word which causes it to assume the next line is a password
	mask - is the character to print in place of the actual password character
	color - colored prompt and feedback");
	E.g - java ${this.getClass().getName()} simple su '*' 
		will use the simple completer with 'su' triggering the use of '*' as a password mask."""
}

try {
	Character mask = null;
	String trigger = null;
	boolean color = false;

	ConsoleReader reader = new ConsoleReader();

	reader.setPrompt("prompt> ");

	if ((args == null) || (args.length == 0)) {
		usage();

		return;
	}

	List<Completer> completors = new LinkedList<Completer>();

	if (args.length > 0) {
		if (args[0].equals("none")) {
		}
		else if (args[0].equals("files")) {
			completors.add(new FileNameCompleter());
		}
		else if (args[0].equals("simple")) {
			completors.add(new StringsCompleter("foo", "bar", "baz"));
		}
		else if (args[0].equals("color")) {
			color = true;
			reader.setPrompt("\u001B[1mfoo\u001B[0m@bar\u001B[32m@baz\u001B[0m> ");
		}
		else {
			usage();

			return;
		}
	}

	if (args.length == 3) {
		mask = args[2].charAt(0);
		trigger = args[1];
	}

	for (Completer c : completors) {
		reader.addCompleter(c);
	}

	String line;
	PrintWriter out = new PrintWriter(reader.getOutput());

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
catch (Throwable t) {
	t.printStackTrace();
}
