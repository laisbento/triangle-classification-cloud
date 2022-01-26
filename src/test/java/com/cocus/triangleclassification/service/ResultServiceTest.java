package com.cocus.triangleclassification.service;

import com.cocus.triangleclassification.exception.ResultTypeException;
import com.cocus.triangleclassification.model.response.ResultsDynamoTable;
import com.cocus.triangleclassification.repository.ResultsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ResultServiceTest {

    @InjectMocks
    private ResultService resultService;

    @Mock
    private ResultsRepository resultsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String MOCKS_RESULTS_JSON = "src/test/java/com/cocus/triangleclassification/mocks/Results.json";

    @Test
    void should_return_all_results() throws IOException {
        Collection<ResultsDynamoTable> resultsDynamoTables = objectMapper.readValue(new File(MOCKS_RESULTS_JSON), new TypeReference<>() {
        });
        when(resultsRepository.findAll()).thenReturn(resultsDynamoTables);

        Collection<ResultsDynamoTable> allResults = resultService.getAllResults();

        verify(resultsRepository, times(1)).findAll();
        assertNotNull(allResults);
        assertEquals(resultsDynamoTables, allResults);
    }

    @Test
    void should_throw_result_type_exception_when_getting_results_on_dynamo_fails() {
        when(resultsRepository.findAll()).thenThrow(RuntimeException.class);

        ResultTypeException thrown = assertThrows(ResultTypeException.class, () -> {
            resultService.getAllResults();
        }, "Unable to get the results from Dynamo");

        verify(resultsRepository, times(1)).findAll();
        assertEquals("Unable to get the results from Dynamo", thrown.getMessage());
    }
}