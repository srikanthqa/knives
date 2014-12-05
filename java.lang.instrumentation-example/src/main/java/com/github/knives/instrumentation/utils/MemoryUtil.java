package com.github.knives.instrumentation.utils;

import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

import com.github.knives.instrumentation.Agent;

public class MemoryUtil {
	public static long memoryUsageOf(Object obj) {
		return Agent.getInstrumentation().getObjectSize(obj);
	}

	public static long deepMemoryUsageOf(Object obj) {
		return deepMemoryUsageOf(obj, VisibilityFilter.NON_PUBLIC);
	}

	public static long deepMemoryUsageOf(Object obj,
			VisibilityFilter referenceFilter) {
		return deepMemoryUsageOf(Agent.getInstrumentation(), new HashSet(),
				obj, referenceFilter);
	}

	public static long deepMemoryUsageOfAll(Collection<? extends Object> objs) {
		return deepMemoryUsageOfAll(objs, VisibilityFilter.NON_PUBLIC);
	}

	public static long deepMemoryUsageOfAll(Collection<? extends Object> objs,
			VisibilityFilter referenceFilter) {
		Instrumentation instr = Agent.getInstrumentation();
		long total = 0L;

		Set counted = new HashSet(objs.size() * 4);
		for (Iterator localIterator = objs.iterator(); localIterator.hasNext();) {
			Object o = localIterator.next();
			total += deepMemoryUsageOf(instr, counted, o, referenceFilter);
		}
		return total;
	}

	private static long deepMemoryUsageOf(Instrumentation instrumentation,
			Set<Integer> counted, Object obj, VisibilityFilter filter)
			throws SecurityException {
		Stack st = new Stack();
		st.push(obj);
		long total = 0L;
		while (!st.isEmpty()) {
			Object o = st.pop();
			if (counted.add(Integer.valueOf(System.identityHashCode(o)))) {
				long sz = instrumentation.getObjectSize(o);
				total += sz;

				Class clz = o.getClass();

				Class compType = clz.getComponentType();
				Object el;
				if ((compType != null) && (!compType.isPrimitive())) {
					Object[] arr = (Object[]) o;
					Object[] arrayOfObject1 = arr;
					for (int i = 0; i < arrayOfObject1.length; i++) {
						el = arrayOfObject1[i];
						if (el != null) {
							st.push(el);
						}
					}

				}

				while (clz != null) {
					for (Field fld : clz.getDeclaredFields()) {
						int mod = fld.getModifiers();

						if (((mod & 0x8) == 0) && (isOf(filter, mod))) {
							Class fieldClass = fld.getType();
							if (!fieldClass.isPrimitive()) {
								if (!fld.isAccessible())
									fld.setAccessible(true);
								try {
									Object subObj = fld.get(o);
									if (subObj != null)
										st.push(subObj);
								} catch (IllegalAccessException illAcc) {
									throw new InternalError("Couldn't read "
											+ fld);
								}
							}
						}
					}
					clz = clz.getSuperclass();
				}
			}
		}
		return total;
	}

	private static boolean isOf(VisibilityFilter f, int mod) {
		switch (f) {
		case ALL:
			return true;
		case PRIVATE_ONLY:
			return false;
		case NONE:
			return (mod & 0x2) != 0;
		case NON_PUBLIC:
			return (mod & 0x1) == 0;
		}
		throw new IllegalArgumentException("Illegal filter " + mod);
	}

	public static enum VisibilityFilter {
		ALL, PRIVATE_ONLY, NON_PUBLIC, NONE;
	}
}
