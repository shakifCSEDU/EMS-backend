package com.example.todo.service.impl;

import com.example.todo.dto.AuthStudentResponse;
import com.example.todo.dto.StudentDto;
import com.example.todo.dto.TeacherStudentDto;
import com.example.todo.dto.TodoDto;
import com.example.todo.entity.*;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.StudentRepository;
import com.example.todo.repository.TeacherRepository;
import com.example.todo.repository.TeacherStudentRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.JwtTokenProvider;
import com.example.todo.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private TeacherStudentRepository teacherStudentRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Override
    public AuthStudentResponse getAllStudentInfo(String token) {
        String userName =  jwtTokenProvider.getUsername(token);
        AuthStudentResponse authStudentResponse = new AuthStudentResponse();

        if(!userName.isEmpty()){
            Optional<User> user =  userRepository.findByUsername(userName);
            authStudentResponse.setId(user.get().getId());
            authStudentResponse.setName(userName);
            authStudentResponse.setStatus(user.get().getStatus());
            // now query the student Table
            Student student = studentRepository.findByUserId(user.get().getId());
            authStudentResponse.setStudent_id(student.getStudent_id());
            authStudentResponse.setDepartment_name(student.getDepartment_name());
            authStudentResponse.setBatch_no(student.getBatch_no());
        }
        return authStudentResponse;
    }

    @Override
    public StudentDto getStudent(Long id) {
        Student student =  studentRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Student not found with id : "+id));
        return modelMapper.map(student, StudentDto.class);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student>students = studentRepository.findAll();

        return students.stream().map((student)->modelMapper.map(student,StudentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Student not found with id : "+id));
        studentRepository.deleteById(id);
    }

    @Override
    public StudentDto activeStudent(Long id) {
        return null;
    }

    @Override
    public StudentDto inActiveStudent(Long id) {
        return null;
    }

    @Override
    public TeacherStudentDto sendRequest(TeacherStudentDto teacherStudentDto) {
        TeacherStudent teacherStudent = new TeacherStudent();

        Long teacher_id  = teacherStudentDto.getTeacher().getTeacher_id();
        Long student_id = teacherStudentDto.getStudent().getStudent_id();



        Teacher teacher = teacherRepository.findById(teacher_id).orElseThrow(
                ()->new ResourceNotFoundException("Teacher not found with id : "+teacher_id)
        );

        Student student =   studentRepository.findById(student_id).orElseThrow(
                ()->new ResourceNotFoundException("Student not found with id : "+student_id)
        );

        teacherStudent.setTeacher(teacher);


        teacherStudent.setStudent(student);
        teacherStudent.setRequest_status("pending");
        TeacherStudent savedTeacherStudent = teacherStudentRepository.save(teacherStudent);

        student.setTeacherStudent(savedTeacherStudent);
        studentRepository.save(student);

        List<TeacherStudent>list = new ArrayList<>();
        list.add(teacherStudent);
        teacher.setTeacherStudents(list);
        teacherRepository.save(teacher);

        return modelMapper.map(savedTeacherStudent,TeacherStudentDto.class);
    }

    @Override
    public String checkStatus(Long student_id) {
        List<TeacherStudent>teacherStudents =  teacherStudentRepository.findAll();
        String status = null;

        for(TeacherStudent teacherStudent : teacherStudents){
            if(teacherStudent.getStudent().getStudent_id().equals(student_id)){
                    status = teacherStudent.getRequest_status();
                    break;
            }
        }
        return status;
    }

    @Override
    public StudentDto updateStudent(StudentDto studentDto) {
        List<Student>students = studentRepository.findAll();
        Student trackStudent = new Student();

        for(Student student : students){
            if(student.getStudent_id().equals(studentDto.getStudent_id())){
                trackStudent = student;
                break;
            }
        }
        trackStudent.getUser().setEmail(studentDto.getUser().getEmail());

        // check the password field is empty or not
        if(StringUtils.hasLength(studentDto.getUser().getPassword()))
            trackStudent.getUser().setPassword(passwordEncoder.encode(studentDto.getUser().getPassword()));


        trackStudent.getUser().setUsername(studentDto.getUser().getUsername());
        trackStudent.setDepartment_name(studentDto.getDepartment_name());

        Student savedStudent =  studentRepository.save(trackStudent);
        return modelMapper.map(savedStudent, StudentDto.class);

    }

}
