package org.example.attendancelist.model;

import jakarta.persistence.*;

/**
 * Represents the attendance of a student in a specific class date.
 */
@Entity
@Table(name = "attendances")
public class Attendance {

    /**
     * Unique identifier for the attendance record.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    /**
     * The status of the student's attendance (e.g., PRESENT, ABSENT, LATE).
     */
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * The student associated with this attendance record.
     */
    @ManyToOne
    @JoinColumn(name = "studentId")
    private Student student;

    /**
     * The class date for which the attendance is recorded.
     */
    @ManyToOne
    @JoinColumn(name = "classDateId")
    private ClassDate classDate;

    /**
     * Default constructor for JPA.
     */
    public Attendance() {
    }

    /**
     * Parameterized constructor to create an attendance record.
     *
     * @param classDate The class date for which the attendance is recorded.
     * @param student   The student associated with this attendance record.
     * @param status    The status of the student's attendance.
     */
    public Attendance(ClassDate classDate, Student student, Status status) {
        this.status = status;
        this.student = student;
        this.classDate = classDate;
    }

    /**
     * Get the unique identifier for the attendance record.
     *
     * @return The attendance record identifier.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Set the unique identifier for the attendance record.
     *
     * @param id The attendance record identifier.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Get the status of the student's attendance.
     *
     * @return The status of the student's attendance.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set the status of the student's attendance.
     *
     * @param status The status of the student's attendance.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Get the student associated with this attendance record.
     *
     * @return The student associated with this attendance record.
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Set the student associated with this attendance record.
     *
     * @param student The student associated with this attendance record.
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Get the class date for which the attendance is recorded.
     *
     * @return The class date for which the attendance is recorded.
     */
    public ClassDate getClassDate() {
        return classDate;
    }

    /**
     * Set the class date for which the attendance is recorded.
     *
     * @param classDate The class date for which the attendance is recorded.
     */
    public void setClassDate(ClassDate classDate) {
        this.classDate = classDate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Attendance{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", student=" + student +
                ", classDate=" + classDate +
                '}';
    }
}
