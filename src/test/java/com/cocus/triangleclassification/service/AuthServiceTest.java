package com.cocus.triangleclassification.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void should_return_true_if_authentication_is_success() {
        Boolean isAuthorized = authService.validateBasicAuthentication("lais", "1234", "Basic bGFpczoxMjM0");
        assertTrue(isAuthorized);
    }

    @Test
    void should_return_false_if_authentication_fails() {
        Boolean isAuthorized = authService.validateBasicAuthentication("lais", "1234", "Basic bGFpczo0MzIx");
        assertFalse(isAuthorized);
    }
}