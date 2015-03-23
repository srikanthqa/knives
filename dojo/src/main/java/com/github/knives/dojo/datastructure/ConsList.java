package com.github.knives.dojo.datastructure;

import java.util.LinkedList;
import java.util.List;

/**
 * ConsList is a list implemented in appending head node
 *
 */
public interface ConsList<T> {
    public T head();

    public ConsList<T> tail();

    public static <T> ConsList<T> cons(T head) {
        return cons(head, nil());
    }

    public static <T> ConsList<T> cons(T head, ConsList<T> tail) {
        return new Cons<T>(head, tail);
    }

    public static <T> ConsList<T> nil() {
        return new Nil<T>();
    }

    public static <T> List<T> toList(ConsList<T> consList) {
        final List<T> result = new LinkedList<T>();

        while (consList instanceof Cons) {
            result.add(consList.head());
            consList = consList.tail();
        }

        return result;
    }

    final public static class Cons<T> implements ConsList<T> {

        final private T head;
        final private ConsList<T> tail;

        public Cons(T head, ConsList<T> tail) {
            this.head = head;
            this.tail = tail;
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public ConsList<T> tail() {
            return tail;
        }
    }

    final public static class Nil<T> implements ConsList<T> {

        @Override
        public T head() {
            throw new IndexOutOfBoundsException();
        }

        @Override
        public ConsList<T> tail() {
            throw new IndexOutOfBoundsException();
        }
    }
}
