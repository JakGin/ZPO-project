package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.util.List;

/**
 * Represents a group of students in the attendance system.
 */
@Entity
@Table(name = "groups")
public class Group {

    /**
     * Unique identifier for the group.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * The name of the group.
     */
    @Column(nullable = false)
    private String name;

    /**
     * List of students belonging to this group.
     */
    @OneToMany(mappedBy = "group")
    private List<Student> students;

    /**
     * List of class dates associated with this group.
     */
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    private List<ClassDate> classDates;

    /**
     * Default constructor for JPA.
     */
    public Group() {}

    /**
     * Parameterized constructor to create a group.
     *
     * @param groupName The name of the group.
     */
    public Group(String groupName) {
        this.name = groupName;
    }

    /**
     * Get the unique identifier for the group.
     *
     * @return The group identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the name of the group.
     *
     * @return The name of the group.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the unique identifier for the group.
     *
     * @param id The group identifier.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the name of the group.
     *
     * @param name The name of the group.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
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
}
