package org.example.attendancelist.repository;

import jakarta.transaction.Transactional;
import org.example.attendancelist.model.Student;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing students.
 *
 * This interface extends CrudRepository to provide basic CRUD operations for the Student entity.
 *
 * @see CrudRepository
 * @see Student
 */
public interface StudentRepository extends CrudRepository<Student, Integer> {

    /**
     * Retrieves students based on name, surname, and group ID.
     *
     * @param name The name of the student.
     * @param surname The surname of the student.
     * @param groupId The ID of the group to which the student belongs.
     * @return Iterable of matching Student records.
     */
    Iterable<Student> findByNameAndSurnameAndGroupId(String name, String surname, Integer groupId);

    /**
     * Retrieves students based on name and surname.
     *
     * @param name The name of the student.
     * @param surname The surname of the student.
     * @return Iterable of matching Student records.
     */
    Iterable<Student> findByNameAndSurname(String name, String surname);

    /**
     * Retrieves students based on name and group ID.
     *
     * @param name The name of the student.
     * @param groupId The ID of the group to which the student belongs.
     * @return Iterable of matching Student records.
     */
    Iterable<Student> findByNameAndGroupId(String name, Integer groupId);

    /**
     * Retrieves students based on surname and group ID.
     *
     * @param surname The surname of the student.
     * @param groupId The ID of the group to which the student belongs.
     * @return Iterable of matching Student records.
     */
    Iterable<Student> findBySurnameAndGroupId(String surname, Integer groupId);

    /**
     * Retrieves students based on name.
     *
     * @param name The name of the student.
     * @return Iterable of matching Student records.
     */
    Iterable<Student> findByName(String name);

    /**
     * Retrieves students based on surname.
     *
     * @param surname The surname of the student.
     * @return Iterable of matching Student records.
     */
    Iterable<Student> findBySurname(String surname);

    /**
     * Retrieves students based on group ID.
     *
     * @param groupId The ID of the group to which the student belongs.
     * @return Iterable of matching Student records.
     */
    Iterable<Student> findByGroupId(Integer groupId);

    /**
     * Updates the group ID to NULL for students belonging to a specified group.
     *
     * @param groupId The ID of the group.
     */
    @Modifying
    @Query("UPDATE Student s SET s.group.id = NULL WHERE s.group.id = :groupId")
    @Transactional
    void updateGroupIdToNull(@Param("groupId") Integer groupId);
}
