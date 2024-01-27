package org.example.attendancelist.controller;

import org.example.attendancelist.model.ClassDate;
import org.example.attendancelist.model.Group;
import org.example.attendancelist.repository.ClassDateRepository;
import org.example.attendancelist.repository.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
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
                            + " on " + classDate.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                            " already exists");
        }

        if (! isDateValid(classDate.getDate())){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Not valid date");
        }

        classDateRepository.save(classDate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("")
    public Iterable<ClassDate> getClassDates(@RequestParam(required = false) Integer id,
                                             @RequestParam(required = false) String date,
                                             @RequestParam(required = false) Integer groupId) {
        if (id != null) {
            Optional<ClassDate> classDate = classDateRepository.findById(id);
            return classDate.map(Collections::singletonList).orElse(Collections.emptyList());
        }

        LocalDateTime convertedDate = null;
        if (date != null) {
            try {
                convertedDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            } catch(DateTimeParseException e) {
                System.out.println(e.getMessage());
                return Collections.emptyList();
            }
        }

        if (groupId != null && convertedDate != null) {
            return classDateRepository.findByGroupIdAndDate(groupId, convertedDate);
        } else if (groupId != null) {
            return classDateRepository.findByGroupId(groupId);
        } else if (convertedDate != null) {
            return classDateRepository.findByDate(convertedDate);
        }

        return classDateRepository.findAll();
    }

    public boolean isDateValid(LocalDateTime date) {
        if (date.getSecond() != 0) {
            return false;
        }
        int minutes = date.getMinute();
        return minutes == 0 || minutes == 15 || minutes == 30 || minutes == 45;
    }
}
