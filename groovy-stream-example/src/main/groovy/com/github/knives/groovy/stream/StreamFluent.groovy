package com.github.knives.groovy.stream

import groovy.stream.Stream

// http://timyates.github.io/groovy-stream/

def integers = Stream.from { x++ } using x:1 take 5
println integers.collect()

// list comprehension
def s = Stream.from( x:1..5, y:1..3 )
				.filter { ( x + y ) % ( x + 2 ) == 0 }
				.map { x + y }

println s.collect()