package com.cocus.triangleclassification.service;

import com.cocus.triangleclassification.exception.TriangleTypeException;
import com.cocus.triangleclassification.model.response.ResultsDynamoTable;
import com.cocus.triangleclassification.model.TriangleType;
import com.cocus.triangleclassification.model.request.TriangleRequest;
import com.cocus.triangleclassification.repository.ResultsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TriangleClassificationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriangleClassificationService.class);
    private final ResultsRepository resultsRepository;

    public TriangleClassificationService(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    public TriangleType classify(TriangleRequest triangleRequest) {
        try {
            TriangleType triangleType = TriangleType.getType(triangleRequest);
            saveResult(triangleRequest, triangleType);
            return triangleType;
        } catch (TriangleTypeException e) {
            LOGGER.error("Unable to get triangle type");
            throw e;
        }
    }

    private void saveResult(TriangleRequest triangleRequest, TriangleType triangleType) {
        ResultsDynamoTable resultToSave = ResultsDynamoTable.builder()
                .result(triangleType.toString())
                .request(triangleRequest.toString())
                .build();
        resultsRepository.save(resultToSave);
    }
}
