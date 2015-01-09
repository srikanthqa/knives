package com.github.knives.java.stream;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.junit.Ignore;
import org.junit.Test;

public class StreamTest {

	static public class Person {
		private String fname, lname;

		public Person() { }
		
		public Person(String fname, String lname) {
			this.fname = fname;
			this.lname = lname;
		}

		public String getFname() {
			return fname;
		}

		public String getLname() {
			return lname;
		}
		
		public void setFname(String fname) {
			this.fname = fname;
		}

		public void setLname(String lname) {
			this.lname = lname;
		}
	}
	
	@Test
	public void testNewAsSupplier() {
		Supplier<Person> personConstructor = Person::new;
		assertNotNull(personConstructor.get());
 	}
	
	@Test
	@Ignore
	public void testInfiniteStreamGenerate() {
		Stream.generate(Math::random).forEach(System.out::println);
	}
	
	@Test
	public void testLimit() {
		Stream.generate(Math::random).limit(100).forEach(System.out::println);
		Stream.iterate(0, i -> { return i + 2; }).limit(100).forEach(System.out::println);
	}
	
	@Test
	public void testSkipToTheEnd() {
		Stream.generate(Math::random).limit(100).skip(99).forEach(System.out::println);
	}
	
	@Test
	@Ignore
	public void testInfiniteStreamIterator() {
		Stream.iterate(0, i -> { return i + 2; }).forEach(System.out::println);
	}
	
	@Test
	public void testSilentStream() {
		Stream.empty().forEach(System.out::println);
	}
	
	@Test
	public void testSplitAsStream() {
		// pattern = anything used as delimiter
		Pattern.compile("\\s+")
			.splitAsStream("hello world fox bump test")
			.map(String::trim)
			.forEach(System.out::println);
	}
	
	@Test
	@Ignore
	public void testFilesLine() throws IOException {
		try (Stream<String> lines = Files.lines(Paths.get("/tmp/test.txt"))) {
			lines.forEach(System.out::println);
		}
	}
	
	@Test
	public void testStreamFlatMap() {
		// flatmap flatten all substream together (just like Optional.flatMap flatten Optional together)
		Pattern.compile("\\s+")
			.splitAsStream("hello world fox bump test")
			.flatMap(i -> characterStream(i))
			.forEach(System.out::println);
	}
	
	@Test
	public void testConcat() {
		Stream.concat(characterStream("hello "), characterStream("world")).forEach(System.out::print);
	}

	@Test
	public void testPeek() {
		// non-side effect peek, handy for debugging
		// only peek when the element is access i.e. toArray() in this case
		// unlike foreach
		characterStream("hello ").peek(System.out::print).toArray();
	}
	
	@Test
	public void testDistinct() {
		Stream.of("happy", "happy", "new", "year")
			.distinct()
			.forEach(System.out::println);
	}
	
	@Test
	public void testSort() {
		Stream.of("1", "22", "333", "4444")
			// longest first
			.sorted(Comparator.comparing(String::length).reversed())
			.forEach(System.out::println);
	}
	
	@Test
	public void testMax() {
		// custom max, 
		// even more interesting String::compareToIgnoreCase is equivalent to Comparator
		// i.e. function that return 1 by compare to another instance of same class
		Stream.of("1", "22", "333", "4444")
			.max(String::compareToIgnoreCase)
			.ifPresent(System.out::println);
	}
	
	public static Stream<Character> characterStream(String s) {
		List<Character> result = new ArrayList<>();
		for (char c : s.toCharArray()) result.add(c);
		return result.stream();
	}
}
