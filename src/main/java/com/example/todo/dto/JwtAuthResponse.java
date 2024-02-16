package com.example.todo.dto;

import com.example.todo.security.JwtAuthenticationEntryPoint;
import com.example.todo.security.JwtAuthenticationFilter;

public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private JwtAuthenticationFilter authenticationFilter;

    public JwtAuthResponse() {
    }

    public JwtAuthResponse(String accessToken, String tokenType, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter authenticationFilter) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.authenticationFilter = authenticationFilter;
    }



    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
