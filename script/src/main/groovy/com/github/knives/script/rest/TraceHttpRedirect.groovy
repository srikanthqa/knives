package com.github.knives.script.rest
/*
 * TraceHttpRedirect prints out all http 302 redirect when access a url
 *
 * Example:
 *
 * groovy TraceHttpRedirect.groovy -u http://google.com
 */

import java.io.IOException

import org.apache.http.HttpResponse
import org.apache.http.HttpRequest
import org.apache.http.protocol.HttpContext
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.LaxRedirectStrategy
import org.apache.http.impl.client.HttpClients

final def cli = new CliBuilder(usage: 'TraceHttpRedirect')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.u( longOpt: 'url', argName: 'u', required: true, args: 1, 'absolute url path i.e. http://google.com' )
cli.m( longOpt: 'method', argName: 'm', required: false, args: 1, 'http method you want to execute i.e. POST, GET, PUT')

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

//--------------------------------------------------------------------------
class LogHttpRedirectStrategy extends LaxRedirectStrategy {
	@Override
	public HttpUriRequest getRedirect(HttpRequest request, HttpResponse response, HttpContext context) 
		throws ProtocolException {
		
		final HttpUriRequest httpUriRedirect = super.getRedirect(request, response, context)
		
		println httpUriRedirect.getURI()
		
		return httpUriRedirect
	}
}

final CloseableHttpClient httpclient = HttpClients.custom()
										.setRedirectStrategy(new LogHttpRedirectStrategy())
										.build()
									
final def url = opt.u
final def method = opt.m ? opt.m.toLowerCase() : 'get'			
									
try {
	HttpUriRequest httpUriRequest;
	
	switch (method) {
		case 'post': httpUriRequest = new HttpPost(url); break;
		case 'get': 
		default: httpUriRequest = new HttpGet(url);
	}
	
	println httpUriRequest.getURI()
	
	httpclient.execute(httpUriRequest)
} finally {
	httpclient.close();
}
