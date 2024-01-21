package org.example.attendancelist.controller;

import org.example.attendancelist.model.Student;
import org.example.attendancelist.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/students")
public class StudentController {

    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostMapping(path="")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createStudent(@RequestBody Student student) {
        studentRepository.save(student);
    }

    @GetMapping(path="")
    public Iterable<Student> getStudents() {
        return studentRepository.findAll();
    }
}
