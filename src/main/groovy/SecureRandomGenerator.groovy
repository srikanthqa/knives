import java.security.SecureRandom
import java.math.BigInteger

final def cli = new CliBuilder(usage: 'SecureRandomGenerator')
cli.h( longOpt: 'help', required: false, 'show usage information' )
cli.n( longOpt: 'bits', argName: 'n', required: true, args: 1, 'Number of bits will generated i.e. range [0, 2^n -1]' )
cli.w( longOpt: 'width', argName: 'w', required: true, args: 1, 'Nummer of bits per character i.e. group n bits by w width' )

//--------------------------------------------------------------------------
final def opt = cli.parse(args)
if (!opt) { return }
if (opt.h) {
	cli.usage();
	return
}

final SecureRandom random = new SecureRandom()
println new BigInteger(opt.n as int, random).toString(opt.w as int)

