package org.example.attendancelist.repository;

import org.example.attendancelist.model.Attendance;
import org.example.attendancelist.model.Status;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing attendance records.
 * This interface extends CrudRepository to provide basic CRUD operations for the Attendance entity.
 *
 * @see CrudRepository
 * @see Attendance
 */
public interface AttendanceRepository extends CrudRepository<Attendance, Integer> {

    /**
     * Retrieves attendance records based on studentId, classDateId, and status.
     *
     * @param studentId The ID of the student.
     * @param classDateId The ID of the class date.
     * @param status The status of the attendance record.
     * @return Iterable of matching Attendance records.
     */
    Iterable<Attendance> findByStudentIdAndClassDateIdAndStatus(Integer studentId, Integer classDateId, Status status);

    /**
     * Retrieves attendance records based on studentId and classDateId.
     *
     * @param studentId The ID of the student.
     * @param classDateId The ID of the class date.
     * @return Iterable of matching Attendance records.
     */
    Iterable<Attendance> findByStudentIdAndClassDateId(Integer studentId, Integer classDateId);

    /**
     * Retrieves attendance records based on studentId and status.
     *
     * @param studentId The ID of the student.
     * @param status The status of the attendance record.
     * @return Iterable of matching Attendance records.
     */
    Iterable<Attendance> findByStudentIdAndStatus(Integer studentId, Status status);

    /**
     * Retrieves attendance records based on classDateId and status.
     *
     * @param classDateId The ID of the class date.
     * @param status The status of the attendance record.
     * @return Iterable of matching Attendance records.
     */
    Iterable<Attendance> findByClassDateIdAndStatus(Integer classDateId, Status status);

    /**
     * Retrieves attendance records based on studentId.
     *
     * @param studentId The ID of the student.
     * @return Iterable of matching Attendance records.
     */
    Iterable<Attendance> findByStudentId(Integer studentId);

    /**
     * Retrieves attendance records based on classDateId.
     *
     * @param classDateId The ID of the class date.
     * @return Iterable of matching Attendance records.
     */
    Iterable<Attendance> findByClassDateId(Integer classDateId);

    /**
     * Retrieves attendance records based on status.
     *
     * @param status The status of the attendance record.
     * @return Iterable of matching Attendance records.
     */
    Iterable<Attendance> findByStatus(Status status);
}
