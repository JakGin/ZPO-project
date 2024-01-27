package org.example.attendancelist.repository;

import org.example.attendancelist.model.ClassDate;
import org.example.attendancelist.model.Group;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface ClassDateRepository extends CrudRepository<ClassDate, Integer> {
    boolean existsByGroupAndDate(Group group, LocalDateTime date);

    Iterable<ClassDate> findByGroupIdAndDate(Integer groupId, LocalDateTime convertedDate);

    Iterable<ClassDate> findByGroupId(Integer groupId);

    Iterable<ClassDate> findByDate(LocalDateTime convertedDate);
}
