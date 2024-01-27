package org.example.attendancelist.model;

import jakarta.persistence.*;

@Entity
@Table(name = "attendances")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;
    @ManyToOne
    @JoinColumn(name = "classDateId")
    private ClassDate classDate;

    public Attendance() {
    }

    public Attendance(ClassDate classDate, Student student, Status status) {
        this.status = status;
        this.student = student;
        this.classDate = classDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

