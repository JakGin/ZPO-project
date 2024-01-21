package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ClassDate {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @OneToMany(mappedBy = "classDate")
    private List<Attendance> attendances;
}
