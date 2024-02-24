package com.example.todo.dto;

public class AuthStudentResponse {
    private String name;
    private Long id;
    private Long student_id;
    private String batch_no;
    private String department_name;
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getBatch_no() {
        return batch_no;
    }

    public void setBatch_no(String batch_no) {
        this.batch_no = batch_no;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    @Override
    public String toString() {
        return "AuthStudentResponse{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", student_id=" + student_id +
                ", batch_no='" + batch_no + '\'' +
                ", department_name='" + department_name + '\'' +
                '}';
    }
}
