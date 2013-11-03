@Grapes([
	@Grab(group='org.apache.httpcomponents', module='fluent-hc', version='4.3.1')
])


import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType
import org.apache.http.client.fluent.Content
import javax.imageio.ImageIO
import java.awt.Image
import javax.swing.JFrame
import javax.imageio.ImageReadParam
import javax.imageio.ImageReader
import javax.imageio.stream.ImageInputStream
import groovy.swing.SwingBuilder
import groovy.json.JsonSlurper
import java.net.URLEncoder

final def cli = new CliBuilder(usage: 'ShowMap')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.a( longOpt: 'address', argName: 'a', required: true, args: 1, 'address of the place' )
cli.l( longOpt: 'length', argName: 'l', required: false, args: 1, 'height of the map' )
cli.w( longOpt: 'width', argName: 'w', required: false, args: 1, 'width of the map' )
cli.z( longOpt: 'zoom', argName: 'z', required: false, args: 1, 'zoom level from space' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def address = URLEncoder.encode(opt.a, 'UTF-8')
final def height = opt.l ? opt.l as int : 500
final def width = opt.w ? opt.w as int : 500
final def zoom = opt.z ? opt.z as int : 10

final def slurper = new JsonSlurper()

final def geocodingApi = "http://maps.googleapis.com/maps/api/geocode/json?address=${address}&sensor=false"
final def geocodingContent = slurper.parseText(Request.Get(geocodingApi).execute().returnContent().asString())
/*
 * "OK" indicates that no errors occurred; the address was successfully parsed and at least one geocode was returned.
"ZERO_RESULTS" indicates that the geocode was successful but returned no results. This may occur if the geocode was passed a non-existent address or a latlng in a remote location.
"OVER_QUERY_LIMIT" indicates that you are over your quota.
"REQUEST_DENIED" indicates that your request was denied, generally because of lack of a sensor parameter.
"INVALID_REQUEST" generally indicates that the query (address or latlng) is missing.
UNKNOWN_ERROR indicates that the request could not be processed due to a server error. The request may succeed if you try again.
 */

println geocodingContent.status
println geocodingContent.results.size()

final def staticMapApi = "http://maps.googleapis.com/maps/api/staticmap?center=-15.800513,-47.91378&zoom=${zoom}&size=${height}x${width}&sensor=false"

final def staticMapContent = Request.Get(staticMapApi).execute().returnContent()
final Iterator<?> readers = ImageIO.getImageReadersByMIMEType(staticMapContent.getType().getMimeType())
final ImageReader reader = (ImageReader) readers.next()
final ImageInputStream iis = ImageIO.createImageInputStream(staticMapContent.asStream())

reader.setInput(iis, true)
final ImageReadParam param = reader.getDefaultReadParam()
final Image image = reader.read(0, param)

new SwingBuilder().edt {
	frame(title: address, size:[height, width], show: true, 
		defaultCloseOperation: JFrame.EXIT_ON_CLOSE, resizable: false, 
		locationRelativeTo: null) {
		
		label(icon:imageIcon(image : image))
	}
}


