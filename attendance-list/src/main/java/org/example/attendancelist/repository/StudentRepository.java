package org.example.attendancelist.repository;

import org.example.attendancelist.model.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Integer> {

    Iterable<Student> findByNameAndSurnameAndGroupId(String name, String surname, Integer groupId);

    Iterable<Student> findByNameAndSurname(String name, String surname);

    Iterable<Student> findByNameAndGroupId(String name, Integer groupId);

    Iterable<Student> findBySurnameAndGroupId(String surname, Integer groupId);

    Iterable<Student> findByName(String name);

    Iterable<Student> findBySurname(String surname);

    Iterable<Student> findByGroupId(Integer groupId);
}
