import org.apache.http.client.fluent.Request

final def cli = new CliBuilder(usage: 'W3CValidateHtml')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.u( longOpt: 'url', argName: 'u', required: true, args: 1, 'absolute url path i.e. http://google.com' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

//--------------------------------------------------------------------------
final def url = opt.u
final def w3ValidatorApi = "http://validator.w3.org/check?uri=${url}"
final def apiHeaderStatus = 'x-w3c-validator-status'

def response = Request.Get(w3ValidatorApi).execute().returnResponse().getFirstHeader(apiHeaderStatus)
println response