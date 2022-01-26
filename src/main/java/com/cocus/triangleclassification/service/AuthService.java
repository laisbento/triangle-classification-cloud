package com.cocus.triangleclassification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    public Boolean validateBasicAuthentication(String appUserName, String appPassword, String basicAuthHeaderValue) {
        if (StringUtils.hasText(basicAuthHeaderValue) && basicAuthHeaderValue.toLowerCase().startsWith("basic")) {
            // Authorization: Basic base64credentials
            String base64Credentials = basicAuthHeaderValue.substring("Basic".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            // credentials = username:password
            final String[] values = credentials.split(":", 2);

            if (values.length == 2) {
                String username = values[0];
                String password = values[1];
                boolean isValid = appUserName.equals(username) && appPassword.equals(password);
                if (isValid) {
                    LOGGER.info("Login successful for user " + username);
                } else {
                    LOGGER.warn("Login is not valid for user " + username);
                }
                return isValid;
            }
        }
        LOGGER.warn("Login is not valid for user " + appUserName);
        return false;
    }

}
