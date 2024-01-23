package org.example.attendancelist.controller;

import org.example.attendancelist.model.Attendance;
import org.example.attendancelist.model.ClassDate;
import org.example.attendancelist.model.Student;
import org.example.attendancelist.repository.AttendanceRepository;
import org.example.attendancelist.repository.ClassDateRepository;
import org.example.attendancelist.repository.GroupRepository;
import org.example.attendancelist.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/attendances")
public class AttendanceController {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final ClassDateRepository classDateRepository;

    public AttendanceController(AttendanceRepository attendanceRepository,
                                StudentRepository studentRepository,
                                ClassDateRepository classDateRepository,
                                GroupRepository groupRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.classDateRepository = classDateRepository;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createAttendance(@RequestBody Attendance attendance) {
        Optional<Student> student = studentRepository.findById(attendance.getStudent().getId());
        if (student.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student with id " + attendance.getStudent().getId() + " does not exist");
        }

        Optional<ClassDate> classDate = classDateRepository.findById(attendance.getClassDate().getId());
        if (classDate.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Class date with id " + attendance.getClassDate().getId() + " does not exist");
        }

        attendanceRepository.save(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path="")
    public Iterable<Attendance> getAttendance() {
        return attendanceRepository.findAll();
    }
}
