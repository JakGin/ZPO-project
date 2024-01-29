package org.example.attendancelist.repository;

import org.example.attendancelist.model.Group;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for managing groups.
 * This interface extends CrudRepository to provide basic CRUD operations for the Group entity.
 *
 * @see CrudRepository
 * @see Group
 */
public interface GroupRepository extends CrudRepository<Group, Integer> {

    /**
     * Retrieves groups based on both ID and name.
     *
     * @param id The ID of the group.
     * @param name The name of the group.
     * @return Iterable of matching Group records.
     */
    Iterable<Group> findByIdAndName(Integer id, String name);

    /**
     * Retrieves groups based on name.
     *
     * @param name The name of the group.
     * @return Iterable of matching Group records.
     */
    Iterable<Group> findByName(String name);
}
