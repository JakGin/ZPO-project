package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "classDates")
public class ClassDate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false)
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;
    @OneToMany(mappedBy = "classDate", cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    public ClassDate() {
    }

    public ClassDate(LocalDateTime date, Group group) {
        this.date = date;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Group getGroup() {
        return group;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "ClassDate{" +
                "id=" + id +
                ", date=" + date +
                ", group=" + group +
                '}';
    }
}
