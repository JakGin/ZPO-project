package org.example.attendancelist.model;

import jakarta.persistence.*;

@Entity
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
}
