package com.cocus.triangleclassification.controllers;

import com.cocus.triangleclassification.exception.TriangleTypeException;
import com.cocus.triangleclassification.model.TriangleType;
import com.cocus.triangleclassification.model.request.TriangleRequest;
import com.cocus.triangleclassification.model.response.ResultsDynamoTable;
import com.cocus.triangleclassification.model.response.TriangleResponse;
import com.cocus.triangleclassification.repository.ResultsRepository;
import com.cocus.triangleclassification.service.TriangleClassificationService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.File;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
class ClassificationControllerTest {

    private static final String MOCKS_REQUEST_JSON = "src/test/java/com/cocus/triangleclassification/mocks/Request.json";
    private static final String MOCKS_RESPONSE_JSON = "src/test/java/com/cocus/triangleclassification/mocks/Results.json";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ResultsRepository resultsRepository;

    @Autowired
    private TriangleClassificationService triangleClassificationService;

    @Test
    void should_classify_triangle_as_scalene_when_all_sizes_are_different() throws Exception {
        List<TriangleRequest> triangleRequests = objectMapper.readValue(new File(MOCKS_REQUEST_JSON), new TypeReference<>() {});
        List<ResultsDynamoTable> resultsDynamoTable = objectMapper.readValue(new File(MOCKS_RESPONSE_JSON), new TypeReference<>() {});

        when(resultsRepository.save(any())).thenReturn(resultsDynamoTable.get(2));
        mvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMjM0")
                        .content(objectMapper.writeValueAsString(triangleRequests.get(0))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.type", equalTo("SCALENE")));

        verify(resultsRepository, times(1)).save(any());
    }

    @Test
    void should_classify_triangle_as_isosceles_when_two_sizes_are_equal() throws Exception {
        List<TriangleRequest> triangleRequests = objectMapper.readValue(new File(MOCKS_REQUEST_JSON), new TypeReference<>() {
        });
        List<ResultsDynamoTable> resultsDynamoTable = objectMapper.readValue(new File(MOCKS_RESPONSE_JSON), new TypeReference<>() {
        });
        when(resultsRepository.save(any())).thenReturn(resultsDynamoTable.get(1));
        mvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMjM0")
                        .content(objectMapper.writeValueAsString(triangleRequests.get(1))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.type", equalTo("ISOSCELES")));

        verify(resultsRepository, times(1)).save(any());
    }

    @Test
    void should_classify_triangle_as_equilateral_when_all_sizes_are_equal() throws Exception {
        List<TriangleRequest> triangleRequests = objectMapper.readValue(new File(MOCKS_REQUEST_JSON), new TypeReference<>() {
        });
        List<ResultsDynamoTable> resultsDynamoTable = objectMapper.readValue(new File(MOCKS_RESPONSE_JSON), new TypeReference<>() {
        });
        when(resultsRepository.save(any())).thenReturn(resultsDynamoTable.get(0));
        mvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMjM0")
                        .content(objectMapper.writeValueAsString(triangleRequests.get(2))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.type", equalTo("EQUILATERAL")));

        verify(resultsRepository, times(1)).save(any());
    }

    @Test
    void should_throw_result_exception_when_is_not_able_to_classify_triangle() throws Exception {
        List<TriangleRequest> triangleRequests = objectMapper.readValue(new File(MOCKS_REQUEST_JSON), new TypeReference<>() {});
        MockedStatic<TriangleType> triangleTypeMockedStatic = mockStatic(TriangleType.class);
        TriangleTypeException triangleTypeException = new TriangleTypeException("Unknown triangle type");
        when(TriangleType.getType(any())).thenThrow(triangleTypeException);
        mvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMjM0")
                        .content(objectMapper.writeValueAsString(triangleRequests.get(2))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TriangleTypeException));
        triangleTypeMockedStatic.close();
    }

    @Test
    void should_not_get_triangle_type_if_authorization_failed() throws Exception {
        List<TriangleRequest> triangleRequests = objectMapper.readValue(new File(MOCKS_REQUEST_JSON), new TypeReference<>() {
        });

        mvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Basic bGFpczoxMj25")
                        .content(objectMapper.writeValueAsString(triangleRequests.get(2))))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(401));
    }

}