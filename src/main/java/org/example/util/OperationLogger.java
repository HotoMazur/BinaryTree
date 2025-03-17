package org.example.util;

import org.example.binarytree.BinaryTreeImpl;

import java.lang.reflect.Method;
import java.util.logging.Logger;

public class OperationLogger {
    private static final Logger LOGGER = Logger.getLogger(OperationLogger.class.getName());

    public static void logMethod(Object instance, String methodName, Object... args) throws Exception {
        Method method = instance.getClass().getDeclaredMethod(methodName, Object.class);
        if (method.isAnnotationPresent(LogOperation.class)) {
            LogOperation annotation = method.getAnnotation(LogOperation.class);
            String operation = annotation.operation();
            LOGGER.info("Entering operation: " + operation + " with args: " + argsToString(args));

            Object result = method.invoke(instance, args);

            if (annotation.logResult()) {
                LOGGER.info("Exiting operation: " + operation + " with result: " + result);
            } else {
                LOGGER.info("Exiting operation: " + operation);
            }
        } else {
            method.invoke(instance, args);
        }
    }

    private static String argsToString(Object... args) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : args) {
            sb.append(arg).append(", ");
        }
        return !sb.isEmpty() ? sb.substring(0, sb.length() - 2) : "";
    }

    public static void main(String[] args) throws Exception {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();
        OperationLogger.logMethod(tree, "insertNode", 5);
        tree.draw();
    }
}
