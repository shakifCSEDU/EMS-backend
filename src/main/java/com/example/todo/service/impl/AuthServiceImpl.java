package com.example.todo.service.impl;

import com.example.todo.dto.JwtAuthResponse;
import com.example.todo.dto.LoginDto;
import com.example.todo.dto.StudentDto;
import com.example.todo.dto.TeacherDto;
import com.example.todo.entity.Role;
import com.example.todo.entity.Student;
import com.example.todo.entity.Teacher;
import com.example.todo.entity.User;
import com.example.todo.exception.TodoAPIException;
import com.example.todo.repository.RoleRepository;
import com.example.todo.repository.StudentRepository;
import com.example.todo.repository.TeacherRepository;
import com.example.todo.repository.UserRepository;
import com.example.todo.security.JwtTokenProvider;
import com.example.todo.service.AuthService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private StudentRepository studentRepository;
    private TeacherRepository teacherRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private ModelMapper modelMapper;
    public AuthServiceImpl(UserRepository userRepository, StudentRepository studentRepository,
                           TeacherRepository teacherRepository, RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                           JwtTokenProvider jwtTokenProvider,ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.modelMapper = modelMapper;
    }

    @Override
    public String registerStudent(StudentDto studentDto) {

        // check email is already exists in database
        if(userRepository.existsByEmail(studentDto.getUser().getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }
        User user = new User();
        user.setUsername(studentDto.getUser().getUsername());
        user.setEmail(studentDto.getUser().getEmail());

        if(!studentDto.getUser().getPhone().isEmpty())
            user.setPhone(studentDto.getUser().getPhone());

        user.setPassword(passwordEncoder.encode(studentDto.getUser().getPassword()));

        //Role userRole =  roleRepository.findByName("ROLE_USER");
        Role userRole = studentDto.getUser().getRole();
        Role savedRole =  roleRepository.save(userRole);

        user.setRole(savedRole);
        User savedUser =  userRepository.save(user);

        // after completing user table then filled the student table

        Student student = new Student();
        student.setBatch_no(studentDto.getBatch_no());
        student.setDepartment_name(studentDto.getDepartment_name());
        student.setUser(savedUser);
        Student savedStudent = studentRepository.save(student);
        StudentDto savedStudentDto = modelMapper.map(savedStudent,StudentDto.class);

        return "Student register successfull!";
    }

    @Override
    public String registerTeacher(TeacherDto teacherDto) {
        // check email is already exists in database
        if(userRepository.existsByEmail(teacherDto.getUser().getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }
        User user = new User();
        user.setUsername(teacherDto.getUser().getUsername());
        user.setEmail(teacherDto.getUser().getEmail());

        if(!teacherDto.getUser().getPhone().isEmpty())
            user.setPhone(teacherDto.getUser().getPhone());

        user.setPassword(passwordEncoder.encode(teacherDto.getUser().getPassword()));

        //Role userRole =  roleRepository.findByName("ROLE_USER");
        Role userRole = teacherDto.getUser().getRole();
        Role savedRole =  roleRepository.save(userRole);

        user.setRole(savedRole);
        User savedUser =  userRepository.save(user);

        // after completing user table then filled the student table

        Teacher teacher = new Teacher();
        teacher.setDesignation(teacherDto.getDesignation());
        teacher.setFaculty_name(teacherDto.getFaculty_name());

        teacher.setUser(savedUser);
        Teacher savedTeacher =  teacherRepository.save(teacher);
        return "Teacher Registration successfull!";
    }

    @Override
    public JwtAuthResponse login(LoginDto loginDto) {
        Authentication authentication =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token  = jwtTokenProvider.generateToken(authentication);

        Optional<User>userOptional =  userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(),
                loginDto.getUsernameOrEmail());

        String role = null;
        Long id = null;
        String name = null;

        if(userOptional.isPresent()){
            User loggedInUser = userOptional.get();
//            Optional<Role>optionalRole =  loggedInUser.getRoles().stream().findFirst();
//            if(optionalRole.isPresent()){
//                Role userRole = optionalRole.get();
//                role = userRole.getName();
//            }
            role = loggedInUser.getRole().getName();
            name = loggedInUser.getUsername();
            id = loggedInUser.getId();

        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setId(id);
        jwtAuthResponse.setName(name);
        return jwtAuthResponse;
    }
}
