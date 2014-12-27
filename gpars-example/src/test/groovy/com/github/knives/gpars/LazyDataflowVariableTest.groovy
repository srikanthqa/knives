package com.github.knives.gpars

import groovyx.gpars.dataflow.LazyDataflowVariable
import groovyx.gpars.dataflow.Dataflow

import org.junit.Test

class LazyDataflowVariableTest {
	@Test
	void testLazyDdataflowVariable() {
		def moduleA = new LazyDataflowVariable({->
			println "Loading moduleA into memory"
			sleep 3000
			println "Loaded moduleA into memory"
			return "moduleA"
		})
		def moduleB = new LazyDataflowVariable({->
			moduleA.then {
				println "->Loading moduleB into memory, since moduleA is ready"
				sleep 3000
				println "  Loaded moduleB into memory"
				return "moduleB"
			}
		})
		
		def moduleC = new LazyDataflowVariable({->
			moduleA.then {
				println "->Loading moduleC into memory, since moduleA is ready"
				sleep 3000
				println "  Loaded moduleC into memory"
				return "moduleC"
			}
		})
		
		def moduleD = new LazyDataflowVariable({->
			Dataflow.whenAllBound(moduleB, moduleC) { b, c ->
				println "-->Loading moduleD into memory, since moduleB and moduleC are ready"
				sleep 3000
				println "   Loaded moduleD into memory"
				return "moduleD"
			}
		})
		
		println "Nothing loaded so far"
		println "==================================================================="
		println "Load module: " + moduleD.get()
		println "==================================================================="
		println "All requested modules loaded"
	}
}
