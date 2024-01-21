package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "group")
    private List<Student> students;

    @OneToMany(mappedBy = "group")
    private List<ClassDate> classDates;

    public Group() {}

    public Group(String groupName) {
        this.name = groupName;
    }
}
