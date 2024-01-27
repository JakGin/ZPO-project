package org.example.attendancelist;

import org.example.attendancelist.model.Attendance;
import org.example.attendancelist.model.ClassDate;
import org.example.attendancelist.model.Group;
import org.example.attendancelist.model.Student;
import org.example.attendancelist.repository.AttendanceRepository;
import org.example.attendancelist.repository.ClassDateRepository;
import org.example.attendancelist.repository.GroupRepository;
import org.example.attendancelist.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.example.attendancelist.model.Status.*;

@SpringBootApplication
public class AttendanceListApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceListApplication.class, args);
    }

    @Bean
    public CommandLineRunner databaseInitializer(GroupRepository groupRepository,
                                                 StudentRepository studentRepository,
                                                 ClassDateRepository classDateRepository,
                                                 AttendanceRepository attendanceRepository) {
        return (args) -> {
            boolean initializeDateBase = true;
            if (!initializeDateBase) {
                return;
            }

            Group group1 = new Group("Group 1");
            Group group2 = new Group("Group 2");
            Group group3 = new Group("Group 3");

            groupRepository.save(group1);
            groupRepository.save(group2);
            groupRepository.save(group3);

            Student marek = new Student(123123, "Marek", "Aureliusz", group1);
            Student john = new Student(333333, "John", "Travolta", group1);
            Student alice = new Student(123001, "Alice", "Johnson", group1);
            Student bob = new Student(123002, "Bob", "Smith", group1);
            Student charlie = new Student(123003, "Charlie", "Brown", group2);
            Student diana = new Student(123004, "Diana", "Williams", group2);
            Student edward = new Student(123005, "Edward", "Miller", group2);
            Student frank = new Student(123006, "Frank", "Taylor", group2);
            Student grace = new Student(123007, "Grace", "Clark", group2);
            Student henry = new Student(123008, "Henry", "Moore", group2);
            Student isabel = new Student(123009, "Isabel", "Davis", group3);
            Student jack = new Student(123010, "Jack", "White", group3);

            studentRepository.saveAll(List.of(marek, john, alice, bob, charlie, diana, edward, frank, grace, henry, isabel, jack));

            LocalDateTime date1 = LocalDateTime.parse("2021-04-21 12:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date2 = LocalDateTime.parse("2024-04-21 12:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date3 = LocalDateTime.parse("2024-04-22 14:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date4 = LocalDateTime.parse("2024-04-23 15:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date5 = LocalDateTime.parse("2024-04-24 09:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date6 = LocalDateTime.parse("2024-04-25 11:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date7 = LocalDateTime.parse("2024-04-26 16:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date8 = LocalDateTime.parse("2024-04-27 10:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date9 = LocalDateTime.parse("2024-04-28 13:15", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date10 = LocalDateTime.parse("2024-04-29 09:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            LocalDateTime date11 = LocalDateTime.parse("2024-04-30 14:45", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            ClassDate classDate1 = new ClassDate(date1, group1);
            ClassDate classDate2 = new ClassDate(date2, group1);
            ClassDate classDate3 = new ClassDate(date3, group2);
            ClassDate classDate4 = new ClassDate(date4, group3);
            ClassDate classDate5 = new ClassDate(date5, group1);
            ClassDate classDate6 = new ClassDate(date6, group2);
            ClassDate classDate7 = new ClassDate(date7, group3);
            ClassDate classDate8 = new ClassDate(date8, group1);
            ClassDate classDate9 = new ClassDate(date9, group2);
            ClassDate classDate10 = new ClassDate(date10, group3);
            ClassDate classDate11 = new ClassDate(date11, group1);

            classDateRepository.saveAll(List.of(classDate1, classDate2, classDate3, classDate4, classDate5, classDate6, classDate7, classDate8, classDate9, classDate10, classDate11));

            Attendance attendance1 = new Attendance(classDate1, marek, PRESENT);
            Attendance attendance2 = new Attendance(classDate2, john, PRESENT);
            Attendance attendance3 = new Attendance(classDate3, charlie, ABSENT);
            Attendance attendance4 = new Attendance(classDate4, diana, PRESENT);
            Attendance attendance5 = new Attendance(classDate5, edward, LATE);
            Attendance attendance6 = new Attendance(classDate6, frank, ABSENT);
            Attendance attendance7 = new Attendance(classDate7, grace, PRESENT);
            Attendance attendance8 = new Attendance(classDate8, henry, ABSENT);
            Attendance attendance9 = new Attendance(classDate9, isabel, EXCUSED);
            Attendance attendance10 = new Attendance(classDate10, jack, PRESENT);
            Attendance attendance11 = new Attendance(classDate11, frank, PRESENT);

            attendanceRepository.saveAll(List.of(attendance1, attendance2, attendance3, attendance4, attendance5, attendance6, attendance7, attendance8, attendance9, attendance10, attendance11));
        };
    }
}
