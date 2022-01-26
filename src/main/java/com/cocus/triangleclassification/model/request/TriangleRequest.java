package com.cocus.triangleclassification.model.request;

import com.cocus.triangleclassification.model.validator.TriangleShape;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@TriangleShape
public class TriangleRequest {
    @NonNull
    @Positive(message = "Size 1 must be bigger than zero")
    private Integer sizeOne;
    @NonNull
    @Positive(message = "Size 2 must be bigger than zero")
    private Integer sizeTwo;
    @NonNull
    @Positive(message = "Size 3 must be bigger than zero")
    private Integer sizeThree;
}
