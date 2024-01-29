package com.example.client;

/**
 * Represents the attendance of a student on a specific class date.
 */
public class Attendance {

    private Integer id;

    private Status status;

    private Student student;

    private ClassDate classDate;

    /**
     * Constructs an Attendance object with the specified status, student, and class date.
     *
     * @param status    The status of the attendance (PRESENT, ABSENT, EXCUSED, LATE).
     * @param student   The student for whom the attendance is recorded.
     * @param classDate The date of the class for which the attendance is recorded.
     */
    public Attendance(Status status, Student student, ClassDate classDate) {
        this.status = status;
        this.student = student;
        this.classDate = classDate;
    }

    /**
     * Gets the ID of the attendance record.
     *
     * @return The ID of the attendance record.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Sets the ID of the attendance record.
     *
     * @param id The ID to set.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Gets the status of the attendance.
     *
     * @return The status of the attendance (PRESENT, ABSENT, EXCUSED, LATE).
     */
    public String getStatus() {
        return status.toString();
    }
    /**
     * Sets the status of the attendance.
     *
     * @param status The status to set (PRESENT, ABSENT, EXCUSED, LATE).
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    /**
     * Gets the student for whom the attendance is recorded.
     *
     * @return The student for whom the attendance is recorded.
     */
    public Student getStudent() {
        return student;
    }
    /**
     * Sets the student for whom the attendance is recorded.
     *
     * @param student The student to set.
     */
    public void setStudent(Student student) {
        this.student = student;
    }
    /**
     * Gets the class date for which the attendance is recorded.
     *
     * @return The class date for which the attendance is recorded.
     */
    public ClassDate getClassDate() {
        return classDate;
    }
    /**
     * Sets the class date for which the attendance is recorded.
     *
     * @param classDate The class date to set.
     */
    public void setClassDate(ClassDate classDate) {
        this.classDate = classDate;
    }
    /**
     * Returns a string representation of the Attendance object.
     *
     * @return A string representation of the Attendance object.
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
    /**
     * Enum representing possible attendance statuses (PRESENT, ABSENT, EXCUSED, LATE).
     */
    public enum Status {
        /**
         * Student is present.
         */
        PRESENT {
            public String toString() {
                return "PRESENT";
            }
        },
        /**
         * Student is absent.
         */
        ABSENT {
            public String toString() {
                return "ABSENT";
            }
        },
        /**
         * Student is excused.
         */
        EXCUSED {
            public String toString() {
                return "EXCUSED";
            }
        },
        /**
         * Student is late.
         */
        LATE {
            public String toString() {
                return "LATE";
            }
        };
        /**
         * Converts a string to the corresponding Status enum.
         *
         * @param status The string representation of the status.
         * @return The Status enum corresponding to the input string.
         */
        public static Status fromString(String status) {
            return switch (status) {
                case "PRESENT" -> PRESENT;
                case "ABSENT" -> ABSENT;
                case "EXCUSED" -> EXCUSED;
                case "LATE" -> LATE;
                default -> null;
            };
        }
    }
}
