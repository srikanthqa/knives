package com.github.knives.dojo.datastructure;

import java.util.Stack;

public class Tree<T> {

    final private TreeNode<T> root;
    final private Stack<TreeNode<T>> stack = new Stack<TreeNode<T>>();

    private Tree(T value) {
        root = new TreeNode<T>(value);
        stack.push(root);
    }

    public static <T> Tree<T> root(T value) {
        return new Tree<T>(value);
    }

    public TreeNode<T> root() {
        return root;
    }

    public Tree<T> left(T value) {
        TreeNode<T> node = new TreeNode<T>(value);
        stack.peek().setLeft(node);
        return this;
    }

    public Tree<T> right(T value) {
        TreeNode<T> node = new TreeNode<T>(value);
        stack.peek().setRight(node);
        return this;
    }

    // Navigation APIs

    public Tree<T> transverseLeft() {
        if (stack.peek().getLeft() == null) {
            throw new IllegalStateException("left node is null");
        }

        stack.push(stack.peek().getLeft());
        return this;
    }

    public Tree<T> transverseRight() {
        if (stack.peek().getRight() == null) {
            throw new IllegalStateException("left node is null");
        }

        stack.push(stack.peek().getRight());
        return this;
    }

    public Tree<T> transverseParent() {
        stack.pop();
        return this;
    }
}
