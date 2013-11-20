import groovy.transform.Canonical
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@TupleConstructor
@ToString
class Customer {
	String first, last
	int age
	Date since
	Collection favItems
}

println new Customer(first:'Tom', last:'Jones', age:21, since:new Date(), favItems:['Books', 'Games'])
println new Customer('Tom', 'Jones', 21, new Date(), ['Books', 'Games'])
println new Customer('Tom', 'Jones')

def a = new Customer('Tom', 'Jones')
a.age = 11
println a

// @Canonical combine @TupleConstructor, @ToString, @EqualsAndHashCode together
@Canonical 
class AllInOneCustomer {
	String first, last
	int age
	Date since
	Collection favItems = ['Food']
	def object
}

def d = new Date()
def anyObject = new Object()
def c1 = new AllInOneCustomer(first:'Tom', last:'Jones', age:21, since:d, favItems:['Books', 'Games'], object: anyObject)
def c2 = new AllInOneCustomer('Tom', 'Jones', 21, d, ['Books', 'Games'], anyObject)
assert c1 == c2