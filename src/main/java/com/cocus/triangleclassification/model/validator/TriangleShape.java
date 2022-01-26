package com.cocus.triangleclassification.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TriangleShapeValidator.class)
public @interface TriangleShape {
    String message() default "{Sizes are not compatible to a triangle shape}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
