package org.example.attendancelist.controller;

import org.example.attendancelist.model.Attendance;
import org.example.attendancelist.model.ClassDate;
import org.example.attendancelist.model.Status;
import org.example.attendancelist.model.Student;
import org.example.attendancelist.repository.AttendanceRepository;
import org.example.attendancelist.repository.ClassDateRepository;
import org.example.attendancelist.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/attendances")
public class AttendanceController {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final ClassDateRepository classDateRepository;

    public AttendanceController(AttendanceRepository attendanceRepository,
                                StudentRepository studentRepository,
                                ClassDateRepository classDateRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.classDateRepository = classDateRepository;
    }

    @PostMapping("")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createAttendance(@RequestBody Attendance attendance) {
        if (attendance.getStatus() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Status must be specified");
        }
        if (attendance.getStudent() == null || attendance.getStudent().getId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student ID must be specified");
        }

        Optional<Student> student = studentRepository.findById(attendance.getStudent().getId());
        if (student.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student with id " + attendance.getStudent().getId() + " does not exist");
        }

        if (attendance.getClassDate() == null || attendance.getClassDate().getId() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Class date ID must be specified");
        }
        Optional<ClassDate> classDate = classDateRepository.findById(attendance.getClassDate().getId());
        if (classDate.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Class date with id " + attendance.getClassDate().getId() + " does not exist");
        }

        if (student.get().getGroup() == null || classDate.get().getGroup() == null
                || ! student.get().getGroup().getId().equals(classDate.get().getGroup().getId())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Student and class date must belong to the same group");
        }

        attendanceRepository.save(attendance);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(path="")
    public Iterable<Attendance> getAttendance(@RequestParam(required = false) Integer studentId,
                                              @RequestParam(required = false) Integer classDateId,
                                              @RequestParam(required = false) Integer id,
                                              @RequestParam(required = false) String status) {
        if (id != null) {
            Optional<Attendance> attendance = attendanceRepository.findById(id);
            return attendance.map(Collections::singletonList).orElse(Collections.emptyList());
        }

        if (studentId != null && classDateId != null && status != null) {
            return attendanceRepository.findByStudentIdAndClassDateIdAndStatus(studentId, classDateId, Status.valueOf(status));
        } else if (studentId != null && classDateId != null) {
            return attendanceRepository.findByStudentIdAndClassDateId(studentId, classDateId);
        } else if (studentId != null && status != null) {
            return attendanceRepository.findByStudentIdAndStatus(studentId, Status.valueOf(status));
        } else if (classDateId != null && status != null) {
            return attendanceRepository.findByClassDateIdAndStatus(classDateId, Status.valueOf(status));
        } else if (studentId != null) {
            return attendanceRepository.findByStudentId(studentId);
        } else if (classDateId != null) {
            return attendanceRepository.findByClassDateId(classDateId);
        } else if (status != null) {
            return attendanceRepository.findByStatus(Status.valueOf(status));
        }

        return attendanceRepository.findAll();
    }
}
