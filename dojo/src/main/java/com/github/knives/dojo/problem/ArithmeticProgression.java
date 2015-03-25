package com.github.knives.dojo.problem;

import java.util.List;
import java.util.ListIterator;

/**
 * In mathematics, an arithmetic progression (AP) or arithmetic sequence is
 * a sequence of numbers such that the difference between the consecutive terms is constant.
 *
 * For instance, the sequence 5, 7, 9, 11, 13, 15 …
 * is an arithmetic progression with common difference of 2.
 */
public interface ArithmeticProgression {

    /**
     * Given a arithmetic progression with a missing term
     * Find the missing term
     *
     * Constraint:
     * + sequence.size() >= 3
     * + term can be positive or negative
     * + term can be between [-10^6, 10^6]
     */
    public static long findMissingTerms(List<Long> sequence) {
        // the given sequence could have no missing term
        // so the missing term is either before a(1) or after a(n)
        if (isArithmeticSequence(sequence)) {
            // I choose before a(1)
            return sequence.get(0) - (sequence.get(1) - sequence.get(0));
        }

        // otherwise, a simple sum will tell which term is missing
        // s(n) = n * (a(n) + a(n)) / 2
        // a(n) = (n-1) * d + a(1)
        // a(1) = sequence(1)
        final int lastIndex = sequence.size() - 1;
        final long arithmeticSum = (sequence.get(0) + sequence.get(lastIndex)) * (sequence.size() + 1) / 2;
        final long sum = sequence.stream().mapToLong(Long::longValue).sum();

        final long missingTerms = arithmeticSum - sum;
        return missingTerms;

    }

    public static boolean isArithmeticSequence(List<Long> sequence) {
        final long diffConstant = (sequence.get(1) - sequence.get(0));
        final ListIterator<Long> iterator = sequence.listIterator(1);
        long previousTerm = sequence.get(0);
        while (iterator.hasNext()) {
            long currentTerm = iterator.next();
            if (currentTerm - previousTerm != diffConstant) return false;
            previousTerm = currentTerm;
        }
        return true;
    }
}
