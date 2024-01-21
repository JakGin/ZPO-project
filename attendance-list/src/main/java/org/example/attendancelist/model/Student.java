package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Student() {}

    public Student(String name, String surname, Group group) {
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Group getGroup() {
        return group;
    }
}
