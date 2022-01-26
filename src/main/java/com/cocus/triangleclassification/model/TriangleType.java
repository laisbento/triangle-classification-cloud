package com.cocus.triangleclassification.model;

import com.cocus.triangleclassification.exception.TriangleTypeException;
import com.cocus.triangleclassification.model.request.TriangleRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public enum TriangleType {
    EQUILATERAL {
        @Override
        public TriangleType evaluate(TriangleRequest triangleRequest) {
            if (triangleRequest.getSizeOne().equals(triangleRequest.getSizeTwo()) && (triangleRequest.getSizeOne().equals(triangleRequest.getSizeThree()))
                    && (triangleRequest.getSizeTwo().equals(triangleRequest.getSizeThree())))
                return this;
            return null;
        }
    }, ISOSCELES {
        @Override
        public TriangleType evaluate(TriangleRequest triangleRequest) {
            if (triangleRequest.getSizeOne().equals(triangleRequest.getSizeTwo()) || (triangleRequest.getSizeOne().equals(triangleRequest.getSizeThree()))
                    || (triangleRequest.getSizeTwo().equals(triangleRequest.getSizeThree())))
                return this;
            return null;
        }
    }, SCALENE {
        @Override
        public TriangleType evaluate(TriangleRequest triangleRequest) {
            if (!triangleRequest.getSizeOne().equals(triangleRequest.getSizeTwo()) && (!triangleRequest.getSizeOne().equals(triangleRequest.getSizeThree()))
                    && (!triangleRequest.getSizeTwo().equals(triangleRequest.getSizeThree())))
                return this;
            return null;
        }
    };

    public abstract TriangleType evaluate(TriangleRequest triangleRequest);

    private static final Logger LOGGER = LoggerFactory.getLogger(TriangleType.class);

    public static TriangleType getType(TriangleRequest triangleRequest) {
        return Arrays.stream(values())
                .filter(triangle -> Objects.nonNull(triangle.evaluate(triangleRequest)))
                .findFirst()
                .orElseThrow(() -> {
                    LOGGER.error("Parameters did not meet any triangle type");
                    throw new TriangleTypeException("Unknown triangle type");
                });
    }
}
