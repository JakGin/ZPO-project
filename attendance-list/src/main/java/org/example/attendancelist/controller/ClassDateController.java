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

/**
 * Controller class for managing class dates.
 * Provides endpoints for creating and retrieving class dates.
 *
 * @see org.springframework.web.bind.annotation.RestController
 * @see org.springframework.web.bind.annotation.RequestMapping
 */
@RestController
@RequestMapping(path="/api/classDates")
public class ClassDateController {
    private final ClassDateRepository classDateRepository;
    private final GroupRepository groupRepository;

    /**
     * Constructor for ClassDateController.
     *
     * @param classDateRepository Repository for managing class dates.
     * @param groupRepository     Repository for managing groups.
     */
    public ClassDateController(ClassDateRepository classDateRepository, GroupRepository groupRepository) {
        this.classDateRepository = classDateRepository;
        this.groupRepository = groupRepository;
    }

    /**
     * Endpoint for creating a new class date.
     *
     * @param classDate The ClassDate object to be created.
     * @return ResponseEntity indicating the success or failure of the operation.
     */
    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createClassDate(@RequestBody ClassDate classDate) {
        if (classDate.getGroup() == null || classDate.getGroup().getId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Group ID must be specified");
        }

        Optional<Group> group = groupRepository.findById(classDate.getGroup().getId());
        if (group.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Group with id " + classDate.getGroup().getId() + " does not exist");
        }

        if (classDate.getDate() == null || ! isDateValid(classDate.getDate())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Date must be specified and valid (minutes must be 0, 15, 30 or 45 and seconds 0) ");
        }
        if (classDateRepository.existsByGroupAndDate(group.get(), classDate.getDate())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Class date for group " + group.get().getName()
                            + " on " + classDate.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) +
                            " already exists");
        }

        classDateRepository.save(classDate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Endpoint for retrieving class dates based on specified parameters.
     *
     * @param id      The ID of the class date to be retrieved.
     * @param date    The date parameter for filtering class dates.
     * @param groupId The group ID parameter for filtering class dates.
     * @return Iterable of ClassDate objects based on the specified parameters.
     */
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

    /**
     * Checks if the given date is valid for class scheduling.
     *
     * @param date The LocalDateTime object representing the date.
     * @return True if the date is valid; false otherwise.
     */
    public boolean isDateValid(LocalDateTime date) {
        if (date.getSecond() != 0) {
            return false;
        }
        int minutes = date.getMinute();
        return minutes == 0 || minutes == 15 || minutes == 30 || minutes == 45;
    }
}
