package org.example.attendancelist.repository;

import org.example.attendancelist.model.Attendance;
import org.springframework.data.repository.CrudRepository;

public interface AttendanceRepository extends CrudRepository<Attendance, Integer> {
}
