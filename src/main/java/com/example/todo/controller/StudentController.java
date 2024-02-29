package com.example.todo.controller;

import com.example.todo.dto.AuthStudentResponse;
import com.example.todo.dto.JwtAuthResponse;
import com.example.todo.dto.TeacherStudentDto;
import com.example.todo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth/student")
public class StudentController {
   @Autowired
   private StudentService studentService;

    // get All info after successfull login authentication
    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/getAllInfo")
    public ResponseEntity<AuthStudentResponse>getAllStudentInfo(@RequestParam("token") String token){
        System.out.println(token);
        AuthStudentResponse authStudentResponse =
                studentService.getAllStudentInfo(token);
        return ResponseEntity.ok(authStudentResponse);
    }


    // student sent a request to any teacher
    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("/send-request")
    public ResponseEntity<TeacherStudentDto>sendRequestToTeacher(@RequestBody TeacherStudentDto teacherStudentDto){
        TeacherStudentDto savedTeacherStudentDto = studentService.sendRequest(teacherStudentDto);
        return ResponseEntity.ok(savedTeacherStudentDto);
    }

    // check student the request status that
    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("/check-status/{id}")
    public ResponseEntity<String>checkStudentStatus(@PathVariable("id") Long student_id){
        String request_status = studentService.checkStatus(student_id);
        return ResponseEntity.ok(request_status);
    }

}
