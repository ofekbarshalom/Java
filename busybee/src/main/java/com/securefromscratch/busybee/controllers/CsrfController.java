package com.securefromscratch.busybee.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CsrfController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsrfController.class);
    
    public record CsrfTokenResponse(String token, String str, String headerName, String paramName) {}
    
    @GetMapping("/gencsrftoken")
    public ResponseEntity<CsrfTokenResponse> getCsrfToken(HttpServletRequest request) {
        CsrfToken token = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (token == null) {
            LOGGER.error("CSRF token not found in request");
            return ResponseEntity.status(500).build();
        }
        
        LOGGER.debug("CSRF token generated: headerName={}, paramName={}", token.getHeaderName(), token.getParameterName());
        return ResponseEntity.ok(new CsrfTokenResponse(token.getToken(), token.toString(), token.getHeaderName(), token.getParameterName()));
    }
}
