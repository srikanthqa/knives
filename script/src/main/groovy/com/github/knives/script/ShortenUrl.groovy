package com.github.knives.script
/*
 * ShortenUrl shorten the url by using
 * google service through google api
 *
 *https://developers.google.com/url-shortener/v1/getting_started#shorten
 *
 * Example:
 *
 * groovy ShortenUrl.groovy -u http://google.com
 */

import org.apache.http.client.fluent.Request
import org.apache.http.entity.ContentType
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

final def cli = new CliBuilder(usage: 'ShortenUrl')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.u( longOpt: 'url', argName: 'u', required: true, args: 1, 'absolute url path i.e. http://google.com' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final def url = opt.u
final def slurper = new JsonSlurper()
final def builder = new JsonBuilder()
final def GOOGLE_SHORTEN_API = 'https://www.googleapis.com/urlshortener/v1/url'

builder {
	longUrl url
} 

final def httpResponse = Request.Post(GOOGLE_SHORTEN_API)
	.bodyString(builder.toString(), ContentType.APPLICATION_JSON)
	.execute()
	.returnContent()
	.asString();
	
final response = slurper.parseText(httpResponse)
println response.id
