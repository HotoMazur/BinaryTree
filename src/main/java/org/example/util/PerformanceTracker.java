package org.example.util;

import org.example.binarytree.BinaryTreeImpl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PerformanceTracker {
    public static Object trackExecution(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        TrackPerformance annotation = method.getAnnotation(TrackPerformance.class);
        if (annotation == null){
            return method.invoke(target, args);
        }

        long startTime;
        long endTime;
        String unit = annotation.unit();
        Object result;

        switch (unit){
            case "ns" -> {
                startTime = System.nanoTime();
                result  = method.invoke(target, args);
                endTime = System.nanoTime();
            }
            case "s" -> {
                startTime = System.currentTimeMillis() / 1000;
                result  = method.invoke(target, args);
                endTime = System.currentTimeMillis() / 1000;
            }
            default -> {
                startTime = System.currentTimeMillis();
                result  = method.invoke(target, args);
                endTime = System.currentTimeMillis();
            }
        }

        long executionTime = endTime - startTime;
        if (annotation.logExecution()){
            System.out.println("Method " + method.getName() + " took " + executionTime + " " + unit);
        }

        return result;
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();

        java.lang.reflect.Method method = BinaryTreeImpl.class.getMethod("insertNode", Object.class);

        PerformanceTracker.trackExecution(tree, method, new Object[]{5});
        PerformanceTracker.trackExecution(tree, method, new Object[]{12});
        PerformanceTracker.trackExecution(tree, method, new Object[]{14});
        PerformanceTracker.trackExecution(tree, method, new Object[]{16});

        tree.draw();
    }
}
