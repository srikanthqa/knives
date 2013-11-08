import javax.imageio.ImageIO
import java.awt.Dimension
import java.awt.Image
import javax.swing.JFrame
import javax.imageio.ImageReadParam
import javax.imageio.ImageReader
import javax.imageio.stream.ImageInputStream
import groovy.swing.SwingBuilder
import groovy.io.FileType
import java.awt.event.*

final def cli = new CliBuilder(usage: 'ShowMap i.e. groovy ShowMap.groovy -a "1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA"')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.l( longOpt: 'left', argName: 'l', required: true, args: 1, 'directory of images to be displayed on the left')
cli.r( longOpt: 'right', argName: 'r', required: true, args: 1, 'directory of images to be displayed on the right')

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def leftDir = opt.l
final def rightDir = opt.r
final def leftDirFile = new File(opt.l)
final def rightDirFile = new File(opt.r)

if (leftDirFile.exists() == false ||
	rightDirFile.exists() == false ||
	leftDirFile.isDirectory() == false ||
	rightDirFile.isDirectory() == false) {
	
	println "Either ${leftDir} or ${rightDir} does not exist or is not a directory"
	return 
}

final def leftDirCannonicalPath = leftDirFile.canonicalPath
final def leftDirCannonicalPathStrLen = leftDirCannonicalPath.size()
final def rightDirCannonicalPath = rightDirFile.canonicalPath
final def rightDirCannonicalPathStrLen = rightDirCannonicalPath.size()

final def swing = new SwingBuilder()
final def keyListener = [ keyPressed: {  println it  } ] as KeyListener
final def frames = ['left', 'right'].collect {
	swing.frame(title: it, show: true, size:[50, 50], defaultCloseOperation: JFrame.EXIT_ON_CLOSE) {
		borderLayout()
	}	
}

// frames.each { it.addKeyListener(keyListener) }

leftDirFile.eachFileRecurse(FileType.FILES) { final leftFile ->
	final def fileSuffix =  leftFile.name.lastIndexOf('.').with { it != -1 ? leftFile.name[it+1..-1] : null }
	if (fileSuffix) {
		final def readers = ImageIO.getImageReadersBySuffix(fileSuffix)
		final def reader = readers.hasNext() ? readers.next() : null
		
		if (reader) {
			final def filePathRelativeToLeftParent = leftFile.canonicalPath.substring(leftDirCannonicalPathStrLen)
			final def rightFilePath = rightDirCannonicalPath + filePathRelativeToLeftParent
			final def rightFile = new File(rightFilePath)
			if (rightFile.exists() == false) {
				println "${filePathRelativeToLeftParent} does not exists in the right directory, but in the left"
			} else {
				final def param = reader.getDefaultReadParam()
				
				final def images = [leftFile, rightFile].collect { 
					final def iis = ImageIO.createImageInputStream(leftFile.newInputStream())
					reader.setInput(iis, true)
					return reader.read(0, param)
				}
				
				final def dimensions = images.collect { new Dimension(it.getWidth(), it.getHeight()) }
			}
		} else {
			println "Cannot read ${leftFile.canonicalPath} as an image file"
		}
	} else {
		println "Unable to determine suffix for ${leftFile.canonicalPath}"
	}
}
	
// TODO: complete this