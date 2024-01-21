package org.example.attendancelist.repository;

import org.example.attendancelist.model.ClassDate;
import org.springframework.data.repository.CrudRepository;

public interface ClassDateRepository extends CrudRepository<ClassDate, Integer> {
}
