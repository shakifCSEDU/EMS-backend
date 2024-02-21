package com.example.todo.dto;

import com.example.todo.security.JwtAuthenticationEntryPoint;
import com.example.todo.security.JwtAuthenticationFilter;

public class JwtAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String role;
    private String name;
    private Long id;
    private Long student_id;
    private String batch_no;
    private String department_name;
    private Long teacher_id;
    private String designation;
    private String faculty_name;

    public JwtAuthResponse() {
    }

    public JwtAuthResponse(String accessToken, String tokenType, String role, String name, Long id,
                           Long student_id, String batch_no, String department_name,
                           Long teacher_id, String designation, String faculty_name) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.role = role;
        this.name = name;
        this.id = id;
        this.student_id = student_id;
        this.batch_no = batch_no;
        this.department_name = department_name;
        this.teacher_id = teacher_id;
        this.designation = designation;
        this.faculty_name = faculty_name;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
