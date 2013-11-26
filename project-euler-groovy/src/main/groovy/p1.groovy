sum = 0
(1 .. 999).each { i ->
	if (i % 3 == 0 || i % 5 == 0) {
		sum += i
	}
}
println sum