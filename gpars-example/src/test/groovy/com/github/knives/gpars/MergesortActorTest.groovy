package com.github.knives.gpars

import static groovyx.gpars.actor.Actors.actor
import groovyx.gpars.group.DefaultPGroup

import org.junit.Test

class MergesortActorTest {
	Closure createMessageHandler(def parentActor) {
		return {
			react {List<Integer> message ->
				assert message != null
				switch (message.size()) {
					case 0..1:
						parentActor.send(message)
						break
					case 2:
						if (message[0] <= message[1]) parentActor.send(message)
						else parentActor.send(message[-1..0])
						break
					default:
						def splitList = split(message)
	
						def child1 = actor(createMessageHandler(delegate))
						def child2 = actor(createMessageHandler(delegate))
						child1.send(splitList[0])
						child2.send(splitList[1])
	
						react {message1 ->
							react {message2 ->
								parentActor.send merge(message1, message2)
							}
						}
				}
			}
		}
	}
	
	def split(List<Integer> list) {
		int listSize = list.size()
		int middleIndex = listSize / 2
		def list1 = list[0..<middleIndex]
		def list2 = list[middleIndex..listSize - 1]
		return [list1, list2]
	}
	
	List<Integer> merge(List<Integer> a, List<Integer> b) {
		int i = 0, j = 0
		final int newSize = a.size() + b.size()
		List<Integer> result = new ArrayList<Integer>(newSize)
	
		while ((i < a.size()) && (j < b.size())) {
			if (a[i] <= b[j]) result << a[i++]
			else result << b[j++]
		}
	
		if (i < a.size()) result.addAll(a[i..-1])
		else result.addAll(b[j..-1])
		return result
	}
	
	@Test
	void testMergeSortActor() {
		def console = new DefaultPGroup(1).actor {
			react {
				println "Sorted array: ${it}"
			}
		}
		
		def sorter = actor(createMessageHandler(console))
		sorter.send([1, 5, 2, 4, 3, 8, 6, 7, 3, 9, 5, 3])
		console.join()
	}
}
