package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Student {
    @Id
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances;
}
