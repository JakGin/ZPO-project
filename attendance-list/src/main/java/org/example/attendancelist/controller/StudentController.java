package org.example.attendancelist.controller;

import org.example.attendancelist.model.Group;
import org.example.attendancelist.model.Student;
import org.example.attendancelist.repository.StudentRepository;
import org.example.attendancelist.repository.GroupRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    public StudentController(StudentRepository studentRepository, GroupRepository groupRepository) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
    }

    @PostMapping(path="")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createStudent(@RequestBody Student student) {
        if (! isIdValid(student.getId())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student ID must be 6 digits long");
        }

        Optional<Group> group = groupRepository.findById(student.getGroup().getId());
        if (group.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Group with id " + student.getGroup().getId() + " does not exist");
        }

        studentRepository.save(student);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path="")
    public Iterable<Student> getStudents(@RequestParam(required = false) Integer id, @RequestParam(required = false) String name,
                                         @RequestParam(required = false) String surname, @RequestParam(required = false) Integer groupId) {
        if (id != null) {
            Optional<Student> student = studentRepository.findById(id);
            return student.map(Collections::singletonList).orElse(Collections.emptyList());
        }

        if (name != null && surname != null && groupId != null)
            return studentRepository.findByNameAndSurnameAndGroupId(name, surname, groupId);
        else if (name != null && surname != null)
            return studentRepository.findByNameAndSurname(name, surname);
        else if (name != null && groupId != null)
            return studentRepository.findByNameAndGroupId(name, groupId);
        else if (surname != null && groupId != null)
            return studentRepository.findBySurnameAndGroupId(surname, groupId);
        else if (name != null)
            return studentRepository.findByName(name);
        else if (surname != null)
            return studentRepository.findBySurname(surname);
        else if (groupId != null)
            return studentRepository.findByGroupId(groupId);

        return studentRepository.findAll();
    }

    public static boolean isIdValid(Integer id) {
        return id >= 100000 && id <= 999999;
    }
}
