package com.example.todo.dto;

import com.example.todo.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

public class TeacherDto {
    private Long teacher_id;
    private String faculty_name;
    private String designation;

    private User user;

    public Long getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(Long teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getFaculty_name() {
        return faculty_name;
    }

    public void setFaculty_name(String faculty_name) {
        this.faculty_name = faculty_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
