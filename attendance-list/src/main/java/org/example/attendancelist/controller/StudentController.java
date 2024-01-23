package org.example.attendancelist.controller;

import org.example.attendancelist.model.Group;
import org.example.attendancelist.model.Student;
import org.example.attendancelist.repository.StudentRepository;
import org.example.attendancelist.repository.GroupRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("")
    public Iterable<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Integer id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(student.get());
    }

    public static boolean isIdValid(Integer id) {
        return id >= 100000 && id <= 999999;
    }
}
