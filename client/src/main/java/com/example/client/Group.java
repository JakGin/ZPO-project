package com.example.client;


import java.util.List;

/**
 * Represents a group of students.
 */
public class Group {

    private Integer id;

    private String name;

    private List<Student> students;

    private List<ClassDate> classDates;
    /**
     * Default constructor for Group.
     */
    public Group() {}
    /**
     * Constructs a Group object with the specified group name.
     *
     * @param groupName The name of the group.
     */
    public Group(String groupName) {
        this.name = groupName;
    }
    /**
     * Gets the ID of the group.
     *
     * @return The ID of the group.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Gets the name of the group.
     *
     * @return The name of the group.
     */
    public String getName() {
        return name;
    }
    /**
     * Returns a string representation of the Group object.
     *
     * @return A string representation of the Group object.
     */
    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", students=" + students +
                ", classDates=" + classDates +
                '}';
    }
    /**
     * Sets the ID of the group.
     *
     * @param id The ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Sets the name of the group.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Gets the list of students in the group.
     *
     * @return The list of students in the group.
     */
    public List<Student> getStudents() {
        return students;
    }
    /**
     * Sets the list of students in the group.
     *
     * @param students The list of students to set.
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    /**
     * Gets the list of class dates associated with the group.
     *
     * @return The list of class dates associated with the group.
     */
    public List<ClassDate> getClassDates() {
        return classDates;
    }
    /**
     * Sets the list of class dates associated with the group.
     *
     * @param classDates The list of class dates to set.
     */
    public void setClassDates(List<ClassDate> classDates) {
        this.classDates = classDates;
    }
}
