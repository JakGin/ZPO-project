package org.example.attendancelist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue
    private Integer id;
    @Column
    private String status;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "classDateId")
    private ClassDate classDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public ClassDate getClassDate() {
        return classDate;
    }

    public void setClassDate(ClassDate classDate) {
        this.classDate = classDate;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", student=" + student +
                ", classDate=" + classDate +
                '}';
    }
}
