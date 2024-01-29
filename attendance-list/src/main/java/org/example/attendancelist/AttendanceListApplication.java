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
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import static org.example.attendancelist.model.Status.*;

/**
 * Spring Boot application for managing attendance lists.
 * The application initializes the database with sample data on startup using CommandLineRunner.
 * It creates groups, students, class dates, and attendance records for demonstration purposes.
 *
 * @see org.springframework.boot.autoconfigure.SpringBootApplication
 * @see CommandLineRunner
 */
@SpringBootApplication
public class AttendanceListApplication {
    /**
     * Main method to run the Spring Boot application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(AttendanceListApplication.class, args);
    }

    /**
     * Default constructor for the AttendanceListApplication class.
     */
    public AttendanceListApplication() {}

    /**
     * Bean for initializing the database with sample data.
     * If the database is not empty, the bean does nothing.
     * Otherwise, it creates groups, students, class dates, and attendance records.
     *
     * @param groupRepository      Repository for managing groups.
     * @param studentRepository    Repository for managing students.
     * @param classDateRepository  Repository for managing class dates.
     * @param attendanceRepository Repository for managing attendance records.
     * @return CommandLineRunner for initializing the database.
     */
    @Bean
    public CommandLineRunner databaseInitializer(GroupRepository groupRepository,
                                                 StudentRepository studentRepository,
                                                 ClassDateRepository classDateRepository,
                                                 AttendanceRepository attendanceRepository) {
        return (args) -> {
            if (!isEmptyDatabase(groupRepository, studentRepository, classDateRepository, attendanceRepository)) {
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
        };
    }

    /**
     * Checks if the database is empty.
     *
     * @param groupRepository      Repository for managing groups.
     * @param studentRepository    Repository for managing students.
     * @param classDateRepository  Repository for managing class dates.
     * @param attendanceRepository Repository for managing attendance records.
     * @return {@code true} if the database is empty, {@code false} otherwise.
     */
    private boolean isEmptyDatabase(GroupRepository groupRepository,
                                    StudentRepository studentRepository,
                                    ClassDateRepository classDateRepository,
                                    AttendanceRepository attendanceRepository) {
        return CollectionUtils.isEmpty((Collection<?>) groupRepository.findAll()) &&
               CollectionUtils.isEmpty((Collection<?>) studentRepository.findAll()) &&
               CollectionUtils.isEmpty((Collection<?>) classDateRepository.findAll()) &&
               CollectionUtils.isEmpty((Collection<?>) attendanceRepository.findAll());
    }
}
