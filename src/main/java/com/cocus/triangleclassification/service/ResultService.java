package com.cocus.triangleclassification.service;

import com.cocus.triangleclassification.exception.ResultTypeException;
import com.cocus.triangleclassification.model.response.ResultsDynamoTable;
import com.cocus.triangleclassification.repository.ResultsRepository;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ResultService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriangleClassificationService.class);

    private final ResultsRepository resultsRepository;

    public ResultService(ResultsRepository resultsRepository) {
        this.resultsRepository = resultsRepository;
    }

    public Collection<ResultsDynamoTable> getAllResults() {
        try {
            return IterableUtils.toList(resultsRepository.findAll());
        } catch (Exception e) {
            LOGGER.error("Unable to get the results from Dynamo");
            throw new ResultTypeException("Unable to get the results from Dynamo", e);
        }
    }
}
