package com.example.todo.controller;

import com.example.todo.dto.*;
import com.example.todo.service.StudentService;
import com.example.todo.service.TeacherService;
import com.example.todo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;



    // grant user Teacher Role
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("user-to-role/{id}")
    public ResponseEntity<UserDto>grantUserToTeacher(@RequestBody UserDto userDto, @PathVariable("id") Long id){
        UserDto savedUserDto = userService.grantUserToRole(userDto,id);
        return ResponseEntity.ok(savedUserDto);
    }



    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/students")
    public ResponseEntity<List<StudentDto>>getAllStudents(){
        List<StudentDto>studentDtos = studentService.getAllStudents();
        return ResponseEntity.ok(studentDtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN','STUDENT')")
    @GetMapping("/teachers")
    public ResponseEntity<List<TeacherDto>>getAllTeachers(){
        List<TeacherDto>teacherDtos = teacherService.getAllTeachers();
        return ResponseEntity.ok(teacherDtos);
    }



    // fetch all students based on teacher_id
    @PreAuthorize("hasAnyRole('TEACHER')")
    @GetMapping("/teacher/{id}/students")
    public ResponseEntity<List<StudentDto>>getAllAddedStudents(@PathVariable("id") Long user_id){

        List<StudentDto> studentDtos = teacherService.findStudents(user_id);
        return ResponseEntity.ok(studentDtos);
    }


    // Teacher can cancel Request
    @PreAuthorize("hasAnyRole('TEACHER')")
    @PostMapping("/teacher/cancel_request")
    public ResponseEntity<String>deleteStudent(@RequestBody TeacherStudentDto teacherStudentDto){
        teacherService.deletePendingOrAccepted(teacherStudentDto);
        return ResponseEntity.ok("Cancel Successfully!");
    }

    // Teacher can accept Request
    @PreAuthorize("hasAnyRole('TEACHER')")
    @PostMapping ("/teacher/accept")
    public ResponseEntity<String>acceptStudent(@RequestBody TeacherStudentDto teacherStudentDto){
        teacherService.acceptPendingRequest(teacherStudentDto);
        return ResponseEntity.ok("Teacher Accept the request.");
    }

    // Teacher Remove student
    @PreAuthorize("hasAnyRole('TEACHER')")
    @PostMapping("/teacher/remove")
    public ResponseEntity<String>removeStudent(@RequestBody TeacherStudentDto teacherStudentDto){
        teacherService.deletePendingOrAccepted(teacherStudentDto);
        return ResponseEntity.ok("Remove Successfully!");
    }

    // Admin activated the user
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/activate-user/{id}")
    public ResponseEntity<String> activeUser(@PathVariable("id") Long user_id){

        String text = userService.activeUser(user_id);
        return ResponseEntity.ok(text);
    }

    // Admin De-activated the user
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deactivate-user/{id}")
    public ResponseEntity<String> DeActiveUser(@PathVariable("id") Long user_id){
       String text = userService.deActiveUser(user_id);
       return ResponseEntity.ok(text);

    }

    // Student update their profile
    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/update-student")
    public ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto){
       // String text = userService.deActiveUser(user_id);
        //return ResponseEntity.ok(text);
       StudentDto savedStudentDto =  studentService.updateStudent(studentDto);
       return ResponseEntity.ok(savedStudentDto);
    }

    // Teacher update their profile
    @PreAuthorize("hasRole('TEACHER')")
    @PostMapping("/update-teacher")
    public ResponseEntity<TeacherDto> updateTeacher(@RequestBody TeacherDto teacherDto){
        TeacherDto teacherDto1 =  teacherService.updateTeacher(teacherDto);
        return ResponseEntity.ok(teacherDto1);
    }

}

