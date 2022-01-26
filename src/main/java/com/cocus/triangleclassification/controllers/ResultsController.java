package com.cocus.triangleclassification.controllers;

import com.cocus.triangleclassification.model.response.ResultsDynamoTable;
import com.cocus.triangleclassification.service.ResultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping
@Api(value = "Triangle Classification Results")
public class ResultsController {

    private final ResultService resultService;

    public ResultsController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping
    @ApiOperation(value = "Get all triangle results")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success!"),
            @ApiResponse(code = 401, message = "You are not authorized!"),
            @ApiResponse(code = 500, message = "Hmmm... I could not complete the request.")
    })
    public Collection<ResultsDynamoTable> getResults() {
        return resultService.getAllResults();
    }
}
