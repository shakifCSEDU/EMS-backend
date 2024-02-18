package com.example.todo.repository;

import com.example.todo.dto.TeacherStudentDto;
import com.example.todo.entity.TeacherStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherStudentRepository extends JpaRepository<TeacherStudent,Long>{


}
