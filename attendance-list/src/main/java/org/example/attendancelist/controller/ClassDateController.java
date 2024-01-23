package org.example.attendancelist.controller;

import org.example.attendancelist.model.ClassDate;
import org.example.attendancelist.model.Group;
import org.example.attendancelist.repository.ClassDateRepository;
import org.example.attendancelist.repository.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/classDates")
public class ClassDateController {
    private final ClassDateRepository classDateRepository;
    private final GroupRepository groupRepository;

    public ClassDateController(ClassDateRepository classDateRepository, GroupRepository groupRepository) {
        this.classDateRepository = classDateRepository;
        this.groupRepository = groupRepository;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createClassDate(@RequestBody ClassDate classDate) {
        Optional<Group> group = groupRepository.findById(classDate.getGroup().getId());
        if (group.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Group with id " + classDate.getGroup().getId() + " does not exist");
        }

        if (classDateRepository.existsByGroupAndDate(group.get(), classDate.getDate())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Class date for group " + group.get().getName()
                            + " on " + classDate.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                            " already exists");
        }

        classDateRepository.save(classDate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path="")
    public Iterable<ClassDate> getClassDates() {
        return classDateRepository.findAll();
    }

    @GetMapping(path="{id}")
    public ResponseEntity<ClassDate> getClassDate(@PathVariable Integer id) {
        Optional<ClassDate> classDate = classDateRepository.findById(id);
        if (classDate.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(classDate.get());
    }
}
