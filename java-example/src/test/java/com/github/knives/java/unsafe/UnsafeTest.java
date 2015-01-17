package com.github.knives.java.unsafe;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.junit.Test;

import sun.misc.Unsafe;


public class UnsafeTest {

	public static Unsafe getUnsafe() throws Exception {
		Field f = Unsafe.class.getDeclaredField("theUnsafe");
		f.setAccessible(true);
		Unsafe unsafe = (Unsafe) f.get(null);
		
		return unsafe;
	}
	
	@Test
	public void testAvoidInitialization() throws Exception {
		// constructor
		A o1 = new A();
		assertEquals(1, o1.a());

		// reflection
		A o2 = A.class.newInstance();
		assertEquals(1, o2.a()); 

		// unsafe
		A o3 = (A) getUnsafe().allocateInstance(A.class); 
		assertEquals(0, o3.a());
	}

	@Test
	public void testMemoryCorruption() throws Exception {
		Guard guard = new Guard();
		assertFalse(guard.giveAccess());

		// bypass
		Unsafe unsafe = getUnsafe();
		Field f = guard.getClass().getDeclaredField("ACCESS_ALLOWED");
		unsafe.putInt(guard, unsafe.objectFieldOffset(f), 42); // memory corruption

		assertTrue(guard.giveAccess());
	}
	
	@Test
	public void testSizeOf() throws Exception {
		A o1 = new A();
		System.out.println(sizeOf(o1));
	}
	
	@Test
	public void testHiddenPassword() throws Exception {
		String password = new String("l00k@myHor$e");
		String fake = new String(password.replaceAll(".", "?"));
		assertEquals("l00k@myHor$e", password);
		assertEquals("????????????", fake);

		getUnsafe().copyMemory(
		          fake, 0L, null, toAddress(password), sizeOf(password));

		assertEquals("????????????", password);
		assertEquals("????????????", fake);
	}
	
	@Test(expected = IOException.class)
	public void testThrowException() {
		Unsafe unsafe = null;
		try {
			unsafe = getUnsafe();
		} catch (Exception e) { }
		
		unsafe.throwException(new IOException());
	}
	
	public static long sizeOf(Object o) throws Exception {
	    Unsafe u = getUnsafe();
	    HashSet<Field> fields = new HashSet<Field>();
	    Class c = o.getClass();
	    while (c != Object.class) {
	        for (Field f : c.getDeclaredFields()) {
	            if ((f.getModifiers() & Modifier.STATIC) == 0) {
	                fields.add(f);
	            }
	        }
	        c = c.getSuperclass();
	    }

	    // get offset
	    long maxSize = 0;
	    for (Field f : fields) {
	        long offset = u.objectFieldOffset(f);
	        if (offset > maxSize) {
	            maxSize = offset;
	        }
	    }

	    return ((maxSize/8) + 1) * 8;   // padding
	}
	
	public static long normalize(int value) {
	    if(value >= 0) return value;
	    return (~0L >>> 32) & value;
	}
	
	public static long toAddress(Object obj) throws Exception {
	    Object[] array = new Object[] {obj};
	    long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);
	    return normalize(getUnsafe().getInt(array, baseOffset));
	}

	public static Object fromAddress(long address) throws Exception {
	    Object[] array = new Object[] {null};
	    long baseOffset = getUnsafe().arrayBaseOffset(Object[].class);
	    getUnsafe().putLong(array, baseOffset, address);
	    return array[0];
	}
	
	public static Object shallowCopy(Object obj) throws Exception {
	    long size = sizeOf(obj);
	    long start = toAddress(obj);
	    long address = getUnsafe().allocateMemory(size);
	    getUnsafe().copyMemory(start, address, size);
	    return fromAddress(address);
	}
	
	static class A {
	    private long a; // not initialized value

	    public A() {
	        this.a = 1; // initialization
	    }

	    public long a() { return this.a; }
	}
	
	static class Guard {
	    private int ACCESS_ALLOWED = 1;

	    public boolean giveAccess() {
	        return 42 == ACCESS_ALLOWED;
	    }
	}
}
