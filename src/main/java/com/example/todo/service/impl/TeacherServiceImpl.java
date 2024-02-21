package com.example.todo.service.impl;

import com.example.todo.dto.StudentDto;
import com.example.todo.dto.TeacherDto;
import com.example.todo.dto.TeacherStudentDto;
import com.example.todo.entity.Student;
import com.example.todo.entity.Teacher;
import com.example.todo.entity.TeacherStudent;
import com.example.todo.entity.User;
import com.example.todo.exception.ResourceNotFoundException;
import com.example.todo.repository.StudentRepository;
import com.example.todo.repository.TeacherRepository;
import com.example.todo.repository.TeacherStudentRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.service.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherStudentRepository teacherStudentRepository;
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public TeacherDto addTeacher(TeacherDto teacherDto) {
        // convert TeacherDto into Teacher Jpa entity
        Teacher teacher = modelMapper.map(teacherDto,Teacher.class);

        User user = teacherDto.getUser();
        User savedUser =  userRepository.save(user);

        teacher.setUser(savedUser);

        Teacher savedTeacher = teacherRepository.save(teacher);

        TeacherDto savedTeacherDto = modelMapper.
                map(savedTeacher,TeacherDto.class);
        return savedTeacherDto;
    }

    @Override
    public TeacherDto getTeacher(Long id) {
        Teacher teacher =  teacherRepository.findById(id).orElseThrow(
                ()->new ResourceNotFoundException("Teacher not found with id : "+id));
        return modelMapper.map(teacher, TeacherDto.class);
    }

    @Override
    public List<TeacherDto> getAllTeachers() {
        List<Teacher>teachers = teacherRepository.findAll();

        return teachers.stream().map((teacher)->modelMapper
                        .map(teacher,TeacherDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDto updateTeacher(TeacherDto teacherDto, Long id) {
        return null;
    }

    @Override
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Teacher not found with id : "+id));
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherDto activeTeacher(Long id) {
        return null;
    }

    @Override
    public TeacherDto inActiveTeacher(Long id) {
        return null;
    }

    @Override
    public List<StudentDto> findStudents(Long teacher_id) {
        // here first all fetch record from TeacherStudent table by the teacher_id
        List<TeacherStudent> dtos = teacherStudentRepository.findAll();

        List<Student>students = new ArrayList<>();
        List<StudentDto>studentDtos = new ArrayList<>();

        for(TeacherStudent teacherStudent:dtos){
            if(teacherStudent.getTeacher().getTeacher_id() == teacher_id){
                StudentDto studentDto = modelMapper.map(teacherStudent.getStudent(),StudentDto.class);
                studentDto.setRequest_status(teacherStudent.getRequest_status());
                studentDtos.add(studentDto);
            }
        }
        return studentDtos;
    }

    // In this method teacher remove or delete student
    @Override
    public void deletePendingOrAccepted(TeacherStudentDto teacherStudentDto) {
        List<TeacherStudent>list = teacherStudentRepository.findAll();
        for(TeacherStudent teacherStudent : list){
            if(teacherStudent.getTeacher().getTeacher_id() == teacherStudentDto.getTeacher().getTeacher_id()){
                if(teacherStudent.getStudent().getStudent_id() == teacherStudentDto.getStudent().getStudent_id()){
                   teacherStudentRepository.delete(teacherStudent);
                }
            }
        }
    }

    @Override
    public void acceptPendingRequest(TeacherStudentDto teacherStudentDto) {
        List<TeacherStudent>list = teacherStudentRepository.findAll();
        TeacherStudent ts;

        for(TeacherStudent teacherStudent : list){
            if(teacherStudent.getTeacher().getTeacher_id() == teacherStudentDto.getTeacher().getTeacher_id()){
                if(teacherStudent.getStudent().getStudent_id() == teacherStudentDto.getStudent().getStudent_id()){
                    if(teacherStudent.getRequest_status().equals("pending")){
                        teacherStudent.setRequest_status("accepted");
                        ts =   teacherStudentRepository.save(teacherStudent);
                        break;
                    }
                }
            }
        }
    }
}
