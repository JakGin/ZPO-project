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
        if (student.getId() == null || ! isIdValid(student.getId())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student ID must be 6 digits long");
        }

        if (student.getGroup() != null && student.getGroup().getId() != null) {
            Optional<Group> group = groupRepository.findById(student.getGroup().getId());
            if (group.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("Group with id " + student.getGroup().getId() + " does not exist");
            }
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

    @DeleteMapping(path="")
    public void deleteStudent(@RequestParam Integer id) {
        studentRepository.deleteById(id);
    }

    @PutMapping(path="/addToGroup")
    public ResponseEntity<String> addStudentToGroup(@RequestParam Integer studentId, @RequestParam Integer groupId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student with id " + studentId + " does not exist");
        }

        Optional<Group> group = groupRepository.findById(groupId);
        if (group.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Group with id " + groupId + " does not exist");
        }

        student.get().setGroup(group.get());
        studentRepository.save(student.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping(path="/deleteFromGroup")
    public ResponseEntity<String> deleteStudentFromGroup(@RequestParam Integer studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student with id " + studentId + " does not exist");
        }

        student.get().setGroup(null);
        studentRepository.save(student.get());

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    public static boolean isIdValid(Integer id) {
        return id >= 100000 && id <= 999999;
    }
}
