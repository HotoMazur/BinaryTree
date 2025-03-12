package org.example.comparator;

import java.util.Comparator;

public class StringComparator extends ComparatorFactory{
    @Override
    public Comparator<String> createComparator() {
        return String::compareTo;
    }
}
