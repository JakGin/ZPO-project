package org.example.attendancelist.repository;

import org.example.attendancelist.model.ClassDate;
import org.example.attendancelist.model.Group;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

/**
 * Repository interface for managing class dates.
 * This interface extends CrudRepository to provide basic CRUD operations for the ClassDate entity.
 *
 * @see CrudRepository
 * @see ClassDate
 */
public interface ClassDateRepository extends CrudRepository<ClassDate, Integer> {

    /**
     * Checks if a class date already exists for a specific group and date.
     *
     * @param group The group associated with the class date.
     * @param date The date of the class.
     * @return True if a class date exists, false otherwise.
     */
    boolean existsByGroupAndDate(Group group, LocalDateTime date);

    /**
     * Retrieves class dates based on groupId and date.
     *
     * @param groupId The ID of the group.
     * @param convertedDate The date of the class.
     * @return Iterable of matching ClassDate records.
     */
    Iterable<ClassDate> findByGroupIdAndDate(Integer groupId, LocalDateTime convertedDate);

    /**
     * Retrieves class dates based on groupId.
     *
     * @param groupId The ID of the group.
     * @return Iterable of matching ClassDate records.
     */
    Iterable<ClassDate> findByGroupId(Integer groupId);

    /**
     * Retrieves class dates based on date.
     *
     * @param convertedDate The date of the class.
     * @return Iterable of matching ClassDate records.
     */
    Iterable<ClassDate> findByDate(LocalDateTime convertedDate);
}
