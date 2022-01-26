package com.cocus.triangleclassification.model.validator;

import com.cocus.triangleclassification.model.request.TriangleRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TriangleShapeValidator implements ConstraintValidator<TriangleShape, TriangleRequest> {

    @Override
    public boolean isValid(TriangleRequest triangle, ConstraintValidatorContext context) {
        return (triangle.getSizeOne() + triangle.getSizeTwo() >= triangle.getSizeThree()) ||
                (triangle.getSizeTwo() + triangle.getSizeThree() >= triangle.getSizeOne()) ||
                (triangle.getSizeThree() + triangle.getSizeOne() >= triangle.getSizeTwo());
    }
}
