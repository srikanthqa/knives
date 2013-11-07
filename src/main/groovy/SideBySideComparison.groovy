import javax.imageio.ImageIO
import java.awt.Image
import javax.swing.JFrame
import javax.imageio.ImageReadParam
import javax.imageio.ImageReader
import javax.imageio.stream.ImageInputStream
import groovy.swing.SwingBuilder

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

// TODO: complete this