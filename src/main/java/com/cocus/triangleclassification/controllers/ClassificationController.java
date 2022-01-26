package com.cocus.triangleclassification.controllers;

import com.cocus.triangleclassification.model.request.TriangleRequest;
import com.cocus.triangleclassification.model.response.TriangleResponse;
import com.cocus.triangleclassification.service.TriangleClassificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@Api(value = "Triangle Classification")
public class ClassificationController {

    private final TriangleClassificationService triangleClassificationService;

    public ClassificationController(TriangleClassificationService triangleClassificationService) {
        this.triangleClassificationService = triangleClassificationService;
    }

    @PostMapping
    @ApiOperation(value = "Classify the triangle")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success!"),
            @ApiResponse(code = 401, message = "You are not authorized!"),
            @ApiResponse(code = 500, message = "Hmmm... I could not complete the request.")
    })
    public TriangleResponse classify(@RequestBody @Valid TriangleRequest triangleRequest) {
        return new TriangleResponse(triangleClassificationService.classify(triangleRequest));
    }
}
