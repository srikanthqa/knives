package com.github.knives.tinkerpop2

import groovy.transform.TupleConstructor

import org.junit.Test

import com.tinkerpop.pipes.Pipe
import com.tinkerpop.pipes.PipeFunction
import com.tinkerpop.pipes.transform.TransformFunctionPipe
import com.tinkerpop.pipes.util.Pipeline

class PipeTest {
	static class RemoveCharacterPipe implements PipeFunction<String, String> {
		final private String removal
		
		public RemoveCharacterPipe(String removal) {
			this.removal = removal;
		}
		
		String compute(String argument) {
			return argument.replaceAll(removal, "");
		}
	}
	
	static class CountCharactersPipe implements PipeFunction<String, Integer> {
		Integer compute(String argument) {
			return argument.size()
		}
	}
	
	@Test
	void testPipe() {
		Pipe<String,Integer> pipeline = new Pipeline<String,Integer>(
				new TransformFunctionPipe<String,String>(new RemoveCharacterPipe("o")),
				new TransformFunctionPipe<String,Integer>(new CountCharactersPipe()))
			
		pipeline.setStarts(Arrays.asList("marko","josh","peter"))
		
		for(Integer number : pipeline) {
			System.out.println(number);
		}
	}
}
