package com.example.client;

import java.util.List;

/**
 * Represents a student with information such as name, surname, group, and attendance records.
 */
public class Student {

    private Integer id;

    private String name;

    private String surname;

    private Group group;

    private List<Attendance> attendances;
    /**
     * Default constructor for Student.
     */
    public Student() {}
    /**
     * Constructs a Student object with the specified name, surname, and group.
     *
     * @param name    The name of the student.
     * @param surname The surname of the student.
     * @param group   The group to which the student belongs.
     */
    public Student(String name, String surname, Group group) {
        this.name = name;
        this.surname = surname;
        this.group = group;
    }
    /**
     * Gets the ID of the student.
     *
     * @return The ID of the student.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Gets the name of the student.
     *
     * @return The name of the student.
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the surname of the student.
     *
     * @return The surname of the student.
     */
    public String getSurname() {
        return surname;
    }
    /**
     * Gets the group to which the student belongs.
     *
     * @return The group to which the student belongs.
     */
    public Group getGroup() {
        return group;
    }
    /**
     * Returns a string representation of the Student object.
     *
     * @return A string representation of the Student object.
     */
    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", group=" + group +
                ", attendances=" + attendances +
                '}';
    }
    /**
     * Sets the ID of the student.
     *
     * @param id The ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Sets the name of the student.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Sets the surname of the student.
     *
     * @param surname The surname to set.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    /**
     * Sets the group to which the student belongs.
     *
     * @param group The group to set.
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    /**
     * Gets the list of attendance records for the student.
     *
     * @return The list of attendance records for the student.
     */
    public List<Attendance> getAttendances() {
        return attendances;
    }
    /**
     * Sets the list of attendance records for the student.
     *
     * @param attendances The list of attendance records to set.
     */
    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
}
