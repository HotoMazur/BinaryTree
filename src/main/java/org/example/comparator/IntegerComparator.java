package org.example.comparator;

import java.util.Comparator;

public class IntegerComparator extends ComparatorFactory {

    @Override
    public Comparator<Integer> createComparator() {
        return Integer::compare;
    }
}
