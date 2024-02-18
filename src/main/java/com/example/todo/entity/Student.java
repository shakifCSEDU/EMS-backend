package com.example.todo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long student_id;


    private String department_name;
    private String batch_no;

    @OneToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "advisor_id",referencedColumnName = "teacher_id")
    private Teacher teacher;

    @OneToOne
    @JoinColumn(name = "teach_stud_id",referencedColumnName = "id")
    private TeacherStudent teacherStudent;

    public TeacherStudent getTeacherStudent() {
        return teacherStudent;
    }

    public void setTeacherStudent(TeacherStudent teacherStudent) {
        this.teacherStudent = teacherStudent;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
