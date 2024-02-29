package com.example.todo.service;

import com.example.todo.dto.*;

import java.util.List;

public interface TeacherService {
    AuthTeacherResponse getAllTeacherInfo(String token);
    TeacherDto getTeacher(Long id);
    List<TeacherDto> getAllTeachers();
    TeacherDto updateTeacher(TeacherDto teacherDto);
    void deleteTeacher(Long id);
    TeacherDto activeTeacher(Long id);
    TeacherDto inActiveTeacher(Long id);
    List<StudentDto> findStudents(Long teacher_id);
    void deletePendingOrAccepted(TeacherStudentDto teacherStudentDto);
    void acceptPendingRequest(TeacherStudentDto teacherStudentDto);
}
