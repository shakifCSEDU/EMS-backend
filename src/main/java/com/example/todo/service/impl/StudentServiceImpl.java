package com.example.todo.service.impl;

import com.example.todo.dto.StudentDto;
import com.example.todo.dto.TodoDto;
import com.example.todo.entity.Student;
import com.example.todo.entity.Teacher;
import com.example.todo.entity.Todo;
import com.example.todo.entity.User;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.StudentRepository;
import com.example.todo.repository.TeacherRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private ModelMapper modelMapper;

    @Override
    public StudentDto addStudent(StudentDto studentDto) {
        // convert StudentTodo into Todo Jpa entity
        Student student = modelMapper.map(studentDto,Student.class);

        // extract Userinfo from Student
        User user = studentDto.getUser();
        User savedUser =  userRepository.save(user);

        // extract Teacherinfo from Student
        // Teacher teacher = studentDto.getTeacher();
        //Teacher savedTeacher = teacherRepository.save(teacher);

        student.setUser(savedUser);
        student.setTeacher(null);

        Student savedStudent = studentRepository.save(student);

        // convert saved Todo Jpa entity object into TodoDto object
        StudentDto savedStudentDto = modelMapper.map(savedStudent,StudentDto.class);
        return savedStudentDto;
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
    public StudentDto updateStudent(StudentDto studentDto, Long id) {
        return null;
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
}
