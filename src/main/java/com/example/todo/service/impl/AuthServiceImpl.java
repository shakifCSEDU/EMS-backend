package com.example.todo.service.impl;

import com.example.todo.dto.*;
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
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
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
    public RegisterDto registerUser(RegisterDto registerDto) {
        // check email is already exists in database
        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPhone(registerDto.getPhone());

        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role userRole = registerDto.getRole();

        user.setRole(userRole);
        User savedUser =  userRepository.save(user);

        return modelMapper.map(savedUser,RegisterDto.class);

    }

    public List<UserDto> getAllUnAllotedUser() {
        List<User>users = userRepository.findAll();

        // track those users whose are not alloted any role
        List<UserDto>unallotedRole = new ArrayList<>();

        for(User user : users){
            if(user.getRole() == null){
                UserDto temp = new UserDto();
                temp.setId(user.getId());
                temp.setEmail(user.getEmail());
                temp.setStatus(user.getStatus());
                temp.setUsername(user.getUsername());
                unallotedRole.add(temp);
            }
        }
        return unallotedRole;
    }


    @Override
    public StudentDto registerStudent(StudentDto studentDto) {

        // check email is already exists in database
        if(userRepository.existsByEmail(studentDto.getUser().getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }
        User user = new User();
        user.setUsername(studentDto.getUser().getUsername());
        user.setEmail(studentDto.getUser().getEmail());
        user.setPhone(studentDto.getUser().getPhone());

        user.setPassword(passwordEncoder.encode(studentDto.getUser().getPassword()));

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
        return savedStudentDto;
    }

    @Override
    public TeacherDto registerTeacher(TeacherDto teacherDto) {
        // check email is already exists in database
        if(userRepository.existsByEmail(teacherDto.getUser().getEmail())){
            throw new TodoAPIException(HttpStatus.BAD_REQUEST,"Email is already exists!");
        }
        User user = new User();
        user.setUsername(teacherDto.getUser().getUsername());
        user.setEmail(teacherDto.getUser().getEmail());

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
        return modelMapper.map(savedTeacher,TeacherDto.class);
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

        if(userOptional.isPresent()){
            User loggedInUser = userOptional.get();
            role = loggedInUser.getRole().getName();
        }
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();



        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setAccessToken(token);
        return jwtAuthResponse;
    }

//    @PostConstruct
//    @Override
//    public void createAdminAccount(){
//        Role role =  roleRepository.findByName("ROLE_ADMIN");
//        System.out.println(role.getName().isEmpty());
//
//        if(role == null){
//
//            User user = new User();
//            user.setUsername("admin");
//            user.setEmail("admin@gmail.com");
//            user.setPhone("0187843782");
//
//            user.setPassword(passwordEncoder.encode("admin"));
//
//            //Role userRole =  roleRepository.findByName("ROLE_USER");
//            Role adminRole = new Role();
//            adminRole.setName("ROLE_ADMIN");
//            adminRole.setDescription("Admin account");
//            Role savedRole =  roleRepository.save(adminRole);
//            user.setRole(savedRole);
//            userRepository.save(user);
//        }
//    }


}
