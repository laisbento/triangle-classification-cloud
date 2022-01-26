package com.cocus.triangleclassification.service;

import com.cocus.triangleclassification.exception.TriangleTypeException;
import com.cocus.triangleclassification.model.TriangleType;
import com.cocus.triangleclassification.model.request.TriangleRequest;
import com.cocus.triangleclassification.repository.ResultsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TriangleClassificationServiceTest {

    @InjectMocks
    private TriangleClassificationService triangleClassificationService;

    @Mock
    private ResultsRepository resultsRepository;

    @Test
    void should_classify_triangle() {
        TriangleRequest triangleRequest = new TriangleRequest();
        triangleRequest.setSizeOne(1);
        triangleRequest.setSizeTwo(1);
        triangleRequest.setSizeThree(1);

        TriangleType classify = triangleClassificationService.classify(triangleRequest);

        assertTrue(Objects.nonNull(classify));
        assertEquals(TriangleType.EQUILATERAL, classify);
        verify(resultsRepository, times(1)).save(any());
    }

    @Test
    void should_throw_exception_when_parameters_are_invalid() {
        MockedStatic<TriangleType> triangleTypeMockedStatic = mockStatic(TriangleType.class);
        TriangleTypeException triangleTypeException = new TriangleTypeException("Unknown triangle type");
        when(TriangleType.getType(any())).thenThrow(triangleTypeException);

        TriangleTypeException thrown = assertThrows(TriangleTypeException.class, () -> {
            triangleClassificationService.classify(any());
        });

        verify(resultsRepository, times(0)).save(any());
        assertEquals("Unknown triangle type", thrown.getMessage());
        triangleTypeMockedStatic.close();
    }
}