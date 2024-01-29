package com.example.client;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Represents a specific date of a class, including attendance information.
 */
public class ClassDate {

    private Integer id;

    private LocalDateTime date;

    private Group group;

    private List<Attendance> attendances;
    /**
     * Constructs a ClassDate object with the specified ID, date, group, and attendance list.
     *
     * @param id          The ID of the class date.
     * @param date        The date of the class.
     * @param group       The group associated with the class date.
     * @param attendances The list of attendance records for the class date.
     */
    public ClassDate(Integer id, LocalDateTime date, Group group, List<Attendance> attendances) {
        this.id = id;
        this.date = date;
        this.group = group;
        this.attendances = attendances;
    }
    /**
     * Default constructor for ClassDate.
     */
    public ClassDate() {}
    /**
     * Gets the ID of the class date.
     *
     * @return The ID of the class date.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Sets the ID of the class date.
     *
     * @param id The ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Gets the date of the class.
     *
     * @return The date of the class.
     */
    public LocalDateTime getDate() {
        return date;
    }
    /**
     * Sets the date of the class.
     *
     * @param date The date to set.
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    /**
     * Gets the group associated with the class date.
     *
     * @return The group associated with the class date.
     */
    public Group getGroup() {
        return group;
    }
    /**
     * Sets the group associated with the class date.
     *
     * @param group The group to set.
     */
    public void setGroup(Group group) {
        this.group = group;
    }
    /**
     * Gets the list of attendance records for the class date.
     *
     * @return The list of attendance records for the class date.
     */
    public List<Attendance> getAttendances() {
        return attendances;
    }
    /**
     * Sets the list of attendance records for the class date.
     *
     * @param attendances The list of attendance records to set.
     */
    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
    /**
     * Returns a string representation of the ClassDate object.
     *
     * @return A string representation of the ClassDate object.
     */
    @Override
    public String toString() {
        return "ClassDate{" +
                "id=" + id +
                ", date=" + date +
                ", group=" + group +
                ", attendances=" + attendances +
                '}';
    }
}
