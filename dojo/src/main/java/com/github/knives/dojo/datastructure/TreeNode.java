package com.github.knives.dojo.datastructure;

public class TreeNode<T> {
	private TreeNode<T> left;
	private TreeNode<T> right;
	private T value;

	public TreeNode(T value) {
		setValue(value);
	}

	public TreeNode<T> getLeft() {
		return left;
	}

	public void setLeft(TreeNode<T> left) {
		this.left = left;
	}

	public TreeNode<T> getRight() {
		return right;
	}

	public boolean hasLeft() {
		return getLeft() != null;
	}

	public void setRight(TreeNode<T> right) {
		this.right = right;
	}

	public boolean hasRight() {
		return getRight() != null;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean hasValue() {
		return getValue() != null;
	}

	@Override
	public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[")
                .append("value=")
                .append(getValue())
                .append(", left=");

        if (hasLeft()) {
            builder.append(getLeft());
        } else {
            builder.append("null");
        }

        builder.append(", right=");

        if (hasRight()) {
            builder.append(getRight());
        } else {
            builder.append("null");
        }

        builder.append("]");

		return builder.toString();
	}

}
