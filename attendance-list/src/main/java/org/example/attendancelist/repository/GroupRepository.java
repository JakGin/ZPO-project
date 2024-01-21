package org.example.attendancelist.repository;

import org.example.attendancelist.model.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GroupRepository extends CrudRepository<Group, Integer> {
}
