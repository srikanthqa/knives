package com.github.knives.dojo.problem;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public interface AlgorithmicCrush {
    public static class Operation {
        final private int a;
        final private int b;
        final private long k;

        public Operation(int a, int b, long k) {
            this.a = a;
            this.b = b;
            this.k = k;
        }

        public int a() { return a; }
        public int b() { return b; }
        public long k() { return k; }

        public String toString() {
            return "[" + a + ", " + b + ", " + k + "]";
        }
    }

    public static Comparator<Operation> SORT_BY_START_INDEX = Comparator.comparingInt(Operation::a);
    public static Comparator<Operation> SORT_BY_END_INDEX = Comparator.comparingInt(Operation::b);

    public static long compute(final int N, final Operation[] operations) {
        Arrays.sort(operations, SORT_BY_START_INDEX);

        System.out.println(Arrays.toString(operations));

        final PriorityQueue<Operation> heap = new PriorityQueue<>(SORT_BY_END_INDEX);

        int j = 0;
        long accumulation = 0;
        long maxValue = 0;

        for (int i = 0; i < N; i++) {
            final int index = i + 1;

            while (!heap.isEmpty()) {
                final Operation top = heap.peek();
                System.out.println(top);
                if (top.b() < index) {
                    // take off and substract from accumulation
                    accumulation -= heap.poll().k();
                } else {
                    break;
                }
            }

            for (; j < operations.length; j++) {
                final Operation operation = operations[j];
                System.out.println(operation);

                if (index < operation.a()) {
                    break;
                } else {
                    accumulation += operation.k();
                    heap.offer(operation);
                }
            }

            maxValue = Math.max(maxValue, accumulation);
        }

        return maxValue;
    }
}
