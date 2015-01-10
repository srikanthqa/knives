package com.github.knives.java.stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
	
	@Test
	public void testReduceWithStringConcat() {
		// this reduce using single accumulator of (T, T) -> Optional<T>
		// in case of empty stream => Optional.empty()
		String val = characterStream("pull apart and put together again")
			.map(Object::toString)
			.reduce(String::concat)
			.orElse(null);
		
		assertEquals("pull apart and put together again", val);
	}
	
	@Test
	public void testAdvanceReduceLikeGroovyInject() {
		// in case of empty stream => initial value
		int totalLengthWithoutSpace = Pattern.compile("\\s+")
			.splitAsStream("hello world fox bump test")
			.reduce(0,
					(accumulate, word) -> accumulate + word.length(),
					(acc1, acc2) -> acc1 + acc2)
			.intValue();
		
		assertEquals(21, totalLengthWithoutSpace);
		
		// Rewrite above with collect 
		// collect is more flexible and has more general use cases
		// reduce can always be written in collect
		// reduce use identity value for supplier, BiFunction for accumulator and BinaryOperator for combiner
		// reduce's accumulator and combiner are expected side-effect free
		// collect use any supplier, BiConsumer for accumulator, BiConsumer for combiner
		// collect's accumulator and combiner cannot be side-effect side cause it 
		// modifies internal value of function's value
		
		int totalLengthWithoutSpace2 = Pattern.compile("\\s+")
			.splitAsStream("hello world fox bump test")
			.collect(AtomicInteger::new, 
					(accumulate, word) -> accumulate.getAndAdd(word.length()), 
					(acc1, acc2) -> acc1.getAndAdd(acc2.get()))
			.get();
		
		assertEquals(21, totalLengthWithoutSpace2);
	}
	
	@Test
	public void testCollectorJoiner() {
		String val = characterStream("pull apart and put together again")
			.map(Object::toString)
			.collect(Collectors.joining());
		
		assertEquals("pull apart and put together again", val);
	}
	
	@Test
	public void testCollectorJoinerDelimiter() {
		String val = characterStream("pull apart and put together again")
			.map(Object::toString)
			.map(String::trim)
			.filter(it -> !it.isEmpty())
			.collect(Collectors.joining(" "));
		
		assertEquals("p u l l a p a r t a n d p u t t o g e t h e r a g a i n", val);
	}
	
	@Test
	public void testSummaryStatistic() {
		IntSummaryStatistics summary = Pattern.compile("\\s+")
			.splitAsStream("hello world fox bump test")
			.collect(Collectors.summarizingInt(String::length));
		
		assertEquals(5, summary.getMax());
		assertEquals(3, summary.getMin());
	}
	
	@Test
	public void testCollectToMapIdentity() {
		// map particular attribute as key for 
		
		// override default merge will throw exception
		BinaryOperator<Locale> mergeFunction = (existingValue, newValue) -> newValue;
		
		Map<String, Locale> countryToLocales = Arrays.stream(Locale.getAvailableLocales())
				.collect(Collectors.toMap(Locale::getCountry, 
						Function.identity(),
						mergeFunction));
		
	}
	
	@Test
	public void testGroupingBy() {
		Map<String, List<Locale>> countryToLocales = Arrays.stream(Locale.getAvailableLocales())
				.collect(Collectors.groupingBy(Locale::getCountry));
	}
	
	@Test
	public void testGroupingBySet() {
		// Collectors.mapping cram mapper() and downstream collector() into one collector()
		Map<String, Set<String>> countryToLanguage = Arrays.stream(Locale.getAvailableLocales())
				.collect(Collectors.groupingBy(
						Locale::getCountry,
						Collectors.mapping(Locale::getLanguage, Collectors.toSet())));
		
		System.out.println(countryToLanguage);
	}
	
	@Test
	public void testGroupingByCounting() {
		Map<String, Long> countryToLanguageCount = Arrays.stream(Locale.getAvailableLocales())
				.collect(Collectors.groupingBy(
						Locale::getCountry,
						Collectors.counting()));
		
		System.out.println(countryToLanguageCount);
	}
	
	public static Stream<Character> characterStream(String s) {
		List<Character> result = new ArrayList<>();
		for (char c : s.toCharArray()) result.add(c);
		return result.stream();
	}
}
