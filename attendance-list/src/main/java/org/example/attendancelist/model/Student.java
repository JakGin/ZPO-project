package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Entity representing a student.
 */
@Entity
@Table(name = "students")
public class Student {

    /**
     * Unique identifier for the student.
     */
    @Id
    private Integer id;

    /**
     * The first name of the student.
     */
    @Column(nullable = false)
    private String name;

    /**
     * The last name of the student.
     */
    @Column(nullable = false)
    private String surname;

    /**
     * The group to which the student belongs.
     */
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    /**
     * List of attendances associated with the student.
     */
    @OneToMany(mappedBy = "student")
    private List<Attendance> attendances;

    /**
     * Default constructor.
     */
    public Student() {}

    /**
     * Constructor with parameters.
     *
     * @param id      Unique identifier for the student.
     * @param name    The first name of the student.
     * @param surname The last name of the student.
     * @param group   The group to which the student belongs.
     */
    public Student(Integer id, String name, String surname, Group group) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    /**
     * Get the unique identifier of the student.
     *
     * @return The student's unique identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the first name of the student.
     *
     * @return The first name of the student.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the last name of the student.
     *
     * @return The last name of the student.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Get the group to which the student belongs.
     *
     * @return The group of the student.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Get the list of attendances associated with the student.
     *
     * @return List of attendances.
     */
    public List<Attendance> getAttendances() {
        return attendances;
    }

    /**
     * Set the unique identifier of the student.
     *
     * @param id The student's unique identifier.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the first name of the student.
     *
     * @param name The first name of the student.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the last name of the student.
     *
     * @param surname The last name of the student.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Set the group to which the student belongs.
     *
     * @param group The group of the student.
     */
    public void setGroup(Group group) {
        this.group = group;
    }
}
