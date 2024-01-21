package org.example.attendancelist.repository;

import org.example.attendancelist.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {
}
