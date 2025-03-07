package org.example.util;

import java.util.Comparator;

public class TreeComparator {
    Comparator<Integer> intComparator;

    public <T> int compare(T val1, T val2){
        if (val1.getClass() == Integer.class){
            return intCompare((Integer) val1, (Integer) val2);
        } else if (val1.getClass() == Double.class){
            return doubleCompare((Double) val1, (Double) val2);
        } else if (val1.getClass() == String.class){
            return stringCompare((String) val1, (String) val2);
        }
        return 0;
    }

    private int stringCompare(String val1, String val2) {
        return val1.compareTo(val2);
    }

    private int doubleCompare(Double val1, Double val2) {
        return val1.compareTo(val2);
    }

    private int intCompare(Integer val1, Integer val2){
        return val1.compareTo(val2);
    }
}
