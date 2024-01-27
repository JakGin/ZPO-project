package org.example.attendancelist.repository;

import jakarta.transaction.Transactional;
import org.example.attendancelist.model.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface StudentRepository extends CrudRepository<Student, Integer> {
    Iterable<Student> findByNameAndSurnameAndGroupId(String name, String surname, Integer groupId);
    Iterable<Student> findByNameAndSurname(String name, String surname);
    Iterable<Student> findByNameAndGroupId(String name, Integer groupId);
    Iterable<Student> findBySurnameAndGroupId(String surname, Integer groupId);
    Iterable<Student> findByName(String name);
    Iterable<Student> findBySurname(String surname);
    Iterable<Student> findByGroupId(Integer groupId);
    @Modifying
    @Query("UPDATE Student s SET s.group.id = NULL WHERE s.group.id = :groupId")
    @Transactional
    void updateGroupIdToNull(@Param("groupId") Integer groupId);
}
