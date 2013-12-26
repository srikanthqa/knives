package com.github.knives.script.deep

import com.github.knives.script.virtualfile.VirtualFile
import com.github.knives.script.virtualfile.VirtualFileFactory

class DeepCompare {
	
	public void compare(final def paths) {
		
		final def stacks = paths.collect {
			final def stack = [] as LinkedList<VirtualFile>
			stack.push(VirtualFileFactory.createVirtualFile(it))
			return stack
		}
		
		while (true) {
			
			final def equalies = stacks.collect { final LinkedList<VirtualFile> stack ->
				final VirtualFile virtualFile = stack.peekFirst()
				
				[virtualFile.isContainer()]
			}
			
			break
		}
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'DeepCompare <path/to/dir|file> <path/to/dir|file>')
		cli.h( longOpt: 'help', required: false, 'show usage information' )

		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h || opt.arguments().size() != 2) {
			cli.usage();
			return
		}
		
		new DeepCompare().compare(opt.arguments())
	}
}
