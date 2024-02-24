package com.example.todo.repository;

import com.example.todo.entity.Student;
import com.example.todo.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Teacher findByUserId(Long user_id);
}
