package com.cocus.triangleclassification.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cocus.triangleclassification.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AppInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppInterceptor.class);

    private static final String AUTH_HEADER_PARAMETER_AUTHORIZATION = "authorization";

    @Value("${app.basic.username}")
    private String userName;
    @Value("${app.basic.password}")
    private String password;

    private final AuthService authService;

    public AppInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean isValidBasicAuthRequest = false;

        try {
            String basicAuthHeaderValue = request.getHeader(AUTH_HEADER_PARAMETER_AUTHORIZATION);
            isValidBasicAuthRequest = authService.validateBasicAuthentication(userName, password, basicAuthHeaderValue);
            if (!isValidBasicAuthRequest) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }

        } catch (Exception e) {
            LOGGER.error("Error occured while authenticating request : " + e.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        return isValidBasicAuthRequest;
    }
}