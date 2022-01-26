package com.cocus.triangleclassification.controllers;

import com.cocus.triangleclassification.exception.ResultTypeException;
import com.cocus.triangleclassification.model.request.TriangleRequest;
import com.cocus.triangleclassification.model.response.ResultsDynamoTable;
import com.cocus.triangleclassification.repository.ResultsRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ResultsControllerTest {

    private static final String MOCKS_RESULTS_JSON = "src/test/java/com/cocus/triangleclassification/mocks/Results.json";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResultsRepository resultsRepository;

    @Test
    void should_return_all_results() throws Exception {
        Collection<ResultsDynamoTable> resultsDynamoTables = objectMapper.readValue(new File(MOCKS_RESULTS_JSON), new TypeReference<>() {
        });
        when(resultsRepository.findAll()).thenReturn(resultsDynamoTables);

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMjM0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.[0].result", equalTo("EQUILATERAL")));

        verify(resultsRepository, times(1)).findAll();
    }

    @Test
    void should_throw_result_exception_when_request_to_dynamo_fails() throws Exception {
        doThrow(RuntimeException.class).when(resultsRepository).findAll();

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMjM0"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is5xxServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResultTypeException))
                .andExpect(result -> assertEquals("Unable to get the results from Dynamo", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    void should_not_get_all_results_if_authorization_failed() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMj25"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(401));
    }

}