package com.example.todo.service;

import com.example.todo.dto.AuthStudentResponse;
import com.example.todo.dto.StudentDto;
import com.example.todo.dto.TeacherStudentDto;

import java.util.List;

public interface StudentService {

    AuthStudentResponse getAllStudentInfo(String token);

    StudentDto addStudent(StudentDto studentDto);
    StudentDto getStudent(Long id);
    List<StudentDto> getAllStudents();
    StudentDto updateStudent(StudentDto studentDto);
    void deleteStudent(Long id);
    StudentDto activeStudent(Long id);
    StudentDto inActiveStudent(Long id);
    TeacherStudentDto sendRequest(TeacherStudentDto teacherStudentDto);
    String checkStatus(Long student_id);
}
