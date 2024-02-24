package com.example.todo.repository;

import com.example.todo.dto.StudentDto;
import com.example.todo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long>{
    Student findByUserId(Long user_id);
}
