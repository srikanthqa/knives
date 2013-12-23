package com.github.knives.script.rest
/**
 * ShowMap show a gui display the map for the supplied address
 * 
 * TODO: add a keyclick listener for next/back to go through each result
 */

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

final def cli = new CliBuilder(usage: 'ShowMap i.e. groovy ShowMap.groovy -a "1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA"')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.a( longOpt: 'address', argName: 'a', required: true, args: 1, 'address of the place' )
cli.l( longOpt: 'length', argName: 'l', required: false, args: 1, 'height of the map, default 512' )
cli.w( longOpt: 'width', argName: 'w', required: false, args: 1, 'width of the map, default 512' )
cli.z( longOpt: 'zoom', argName: 'z', required: false, args: 1, 'zoom level from space, default 15' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def address = URLEncoder.encode(opt.a, 'UTF-8')
final def height = opt.l ? opt.l as int : 512
final def width = opt.w ? opt.w as int : 512
final def zoom = opt.z ? opt.z as int : 15

final def slurper = new JsonSlurper()

final def geocodingApi = "http://maps.googleapis.com/maps/api/geocode/json?address=${address}&sensor=false"
final def geocodingContent = slurper.parseText(Request.Get(geocodingApi).execute().returnContent().asString())

if (geocodingContent.status == 'OK') {
	println "Found ${geocodingContent.results.size()} results and display first result"
	
	final def firstResult = geocodingContent.results[0]
	final def resultAddress = firstResult.formatted_address
	final def lat = firstResult.geometry.location.lat
	final def lng = firstResult.geometry.location.lng
	
	final def staticMapApi = "http://maps.googleapis.com/maps/api/staticmap?center=${lat},${lng}&zoom=${zoom}&size=${height}x${width}&sensor=false&markers=size:mid%7Ccolor:blue%7C${lat},${lng}"
	
	final def staticMapContent = Request.Get(staticMapApi).execute().returnContent()
	final Iterator<?> readers = ImageIO.getImageReadersByMIMEType(staticMapContent.getType().getMimeType())
	final ImageReader reader = (ImageReader) readers.next()
	final ImageInputStream iis = ImageIO.createImageInputStream(staticMapContent.asStream())
	
	reader.setInput(iis, true)
	final ImageReadParam param = reader.getDefaultReadParam()
	final Image image = reader.read(0, param)
	final def swing = new SwingBuilder()
	final def frame = swing.frame(title: address, size:[height, width], 
		show: true, 
		defaultCloseOperation: JFrame.EXIT_ON_CLOSE, 
		resizable: false,
		locationRelativeTo: null) {
			label(icon:imageIcon(image : image))
		}
		
} else {
	println geocodingContent.status
}




