package com.example.todo.dto;

import com.example.todo.security.JwtAuthenticationEntryPoint;
import com.example.todo.security.JwtAuthenticationFilter;

public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;


    //private String name;
    //private Long id;
    //private Long student_id;
    //private String batch_no;
    //private String department_name;
    //private Long teacher_id;
    //private String designation;
    //private String faculty_name;
    //private Boolean status;



    public JwtAuthResponse() {
    }

    public JwtAuthResponse(String accessToken, String tokenType, String role, String name, Long id, Long student_id,
                           String batch_no, String department_name, Long teacher_id,
                           String designation, String faculty_name, Boolean status) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.role = role;

    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
