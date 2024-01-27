package org.example.attendancelist.repository;

import org.example.attendancelist.model.Attendance;
import org.example.attendancelist.model.Status;
import org.springframework.data.repository.CrudRepository;

public interface AttendanceRepository extends CrudRepository<Attendance, Integer> {
    Iterable<Attendance> findByStudentIdAndClassDateIdAndStatus(Integer studentId, Integer classDateId, Status status);
    Iterable<Attendance> findByStudentIdAndClassDateId(Integer studentId, Integer classDateId);
    Iterable<Attendance> findByStudentIdAndStatus(Integer studentId, Status status);
    Iterable<Attendance> findByClassDateIdAndStatus(Integer classDateId, Status status);
    Iterable<Attendance> findByStudentId(Integer studentId);
    Iterable<Attendance> findByClassDateId(Integer classDateId);
    Iterable<Attendance> findByStatus(Status status);
}
