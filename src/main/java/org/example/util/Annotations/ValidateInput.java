package org.example.util.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ValidateInput {
    boolean allowNull() default false;
    int minValue() default Integer.MIN_VALUE;
    int maxValue() default Integer.MAX_VALUE;
    boolean enforceTypeConsistency() default true;
}
