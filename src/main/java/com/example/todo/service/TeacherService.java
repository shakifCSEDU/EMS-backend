package com.example.todo.service;

import com.example.todo.dto.StudentDto;
import com.example.todo.dto.TeacherDto;
import com.example.todo.dto.TeacherStudentDto;

import java.util.List;

public interface TeacherService {

    TeacherDto addTeacher(TeacherDto teacherDto);
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
