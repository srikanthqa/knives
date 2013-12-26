package com.github.knives.script.deep

import com.github.knives.script.virtualfile.VirtualFile
import com.github.knives.script.virtualfile.VirtualFileFactory

class DeepCompare {
	
	public void compare(final String leftPath, final String rightPath) {
		VirtualFile leftVirtualFile = VirtualFileFactory.createVirtualFile(leftPath)
		VirtualFile rightVirtualFile = VirtualFileFactory.createVirtualFile(rightPath)
		
		
	}
	
	public static void main(String[] args) {
		final def cli = new CliBuilder(usage: 'DeepCompare <path/to/dir|file> <path/to/dir|file>')
		cli.h( longOpt: 'help', required: false, 'show usage information' )

		//--------------------------------------------------------------------------
		final def opt = cli.parse(args)
		if (!opt) { return }
		if (opt.h || opt.arguments().size() < 2) {
			cli.usage();
			return
		}
		
		def leftPath =  opt.arguments().get(0)
		def rightPath = opt.arguments().get(1)
		
		new DeepCompare().compare(leftPath, rightPath)
	}
}
