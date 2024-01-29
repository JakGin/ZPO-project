package org.example.attendancelist.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a specific date for a class session associated with a group.
 */
@Entity
@Table(name = "classDates")
public class ClassDate {

    /**
     * Unique identifier for the class date.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * The date and time of the class session.
     */
    @Column(nullable = false)
    private LocalDateTime date;

    /**
     * The group associated with this class date.
     */
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    /**
     * List of attendances associated with this class date.
     */
    @OneToMany(mappedBy = "classDate", cascade = CascadeType.ALL)
    private List<Attendance> attendances;

    /**
     * Default constructor for JPA.
     */
    public ClassDate() {
    }

    /**
     * Parameterized constructor to create a class date.
     *
     * @param date  The date and time of the class session.
     * @param group The group associated with this class date.
     */
    public ClassDate(LocalDateTime date, Group group) {
        this.date = date;
        this.group = group;
    }

    /**
     * Get the unique identifier for the class date.
     *
     * @return The class date identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Get the date and time of the class session.
     *
     * @return The date and time of the class session.
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Get the group associated with this class date.
     *
     * @return The group associated with this class date.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Set the unique identifier for the class date.
     *
     * @param id The class date identifier.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Set the date and time of the class session.
     *
     * @param date The date and time of the class session.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Set the group associated with this class date.
     *
     * @param group The group associated with this class date.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "ClassDate{" +
                "id=" + id +
                ", date=" + date +
                ", group=" + group +
                '}';
    }
}
