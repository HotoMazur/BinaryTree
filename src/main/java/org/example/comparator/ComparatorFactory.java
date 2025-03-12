package org.example.comparator;

import java.util.Comparator;

public abstract class ComparatorFactory{
    public abstract <T> Comparator<T>  createComparator();

    static class DoubleComparator extends ComparatorFactory{

        @Override
        public Comparator<Double> createComparator() {
            return Double::compare;
        }
    }
}
