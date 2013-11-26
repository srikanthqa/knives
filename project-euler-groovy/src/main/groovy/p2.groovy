def fib(Closure yield) {
	def a = 0
	def b = 1
	def tmp
	def limit = 4000000
	while(a <= limit || b <= limit) {
		yield(a)
		tmp = a
		a = b
		b = a + tmp
	}
}

sum = 0
fib { i ->
	if (i % 2 == 0) 
		sum += i
}
println sum