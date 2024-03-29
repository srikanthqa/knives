package com.github.knives.dojo.datastructure;

/**
 * An object of this class can be used to store a pair of objects.
 */
public class Pair<T1, T2> {
	private T1 first;

	public T1 getFirst() {
		return first;
	}

	public void setFirst(T1 foo) {
		this.first = foo;
	}

	private T2 second;

	public T2 getSecond() {
		return second;
	}

	public void setSecond(T2 bar) {
		this.second = bar;
	}

	public Pair(T1 first, T2 second) {
		setFirst(first);
		setSecond(second);
	}

	public Pair() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();

		buffer.append("(");
		buffer.append(first == null ? "null" : first.toString());
		buffer.append(",");
		buffer.append(second == null ? "null" : second.toString());
		buffer.append(")");

		return buffer.toString();
	}
}
