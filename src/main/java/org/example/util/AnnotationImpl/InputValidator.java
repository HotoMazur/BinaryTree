package org.example.util.AnnotationImpl;

import org.example.binarytree.BinaryTreeImpl;
import org.example.util.Annotations.ValidateInput;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InputValidator {
    public static Object validateInput(Object target, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        ValidateInput annotation = method.getAnnotation(ValidateInput.class);
        if (annotation == null) {
            return method.invoke(target, args);
        }

        if (args == null || args.length == 0) {
            throw new IllegalArgumentException("No arguments");
        }

        Object input = args[0];

        if (input == null && !annotation.allowNull()) {
            throw new IllegalArgumentException("Null input isn't allowed");
        }

        if (annotation.enforceTypeConsistency() && input != null) {
            if (target instanceof BinaryTreeImpl<?>) {
                BinaryTreeImpl<?> tree = (BinaryTreeImpl<?>) target;
                Class<?> expectedType = tree.getExpectedType();
                if (expectedType != null && !expectedType.equals(input.getClass())) {
                    throw new IllegalArgumentException(
                            "Input type " + input.getClass().getSimpleName() +
                                    " does not match expected type " + expectedType.getSimpleName()
                    );
                }
            }
        }

        if (input instanceof Integer) {
            int value = (Integer) input;
            if (value < annotation.minValue() || value > annotation.maxValue()) {
                throw new IllegalArgumentException(
                        "Input value " + value + " is out of range [" + annotation.minValue() + ", " + annotation.maxValue() + "]"
                );
            }
        }

        return method.invoke(target, args);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        BinaryTreeImpl<Integer> tree = new BinaryTreeImpl<>();

        Method method = BinaryTreeImpl.class.getMethod("insertNode", Object.class);

        InputValidator.validateInput(tree, method, new Object[]{5});
        tree.draw();

        InputValidator.validateInput(tree, method, new Object[]{10});
        tree.draw();

            InputValidator.validateInput(tree, method, new Object[]{101});
            tree.draw();



            InputValidator.validateInput(tree, method, new Object[]{"string"});

    }
}
