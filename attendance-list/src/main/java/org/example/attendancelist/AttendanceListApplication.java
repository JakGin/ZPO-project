package org.example.attendancelist;

import org.example.attendancelist.model.Group;
import org.example.attendancelist.repository.GroupRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AttendanceListApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceListApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(GroupRepository repository) {
        return (args) -> {
            repository.save(new Group("Group 1"));
            repository.save(new Group("Group 2"));
            repository.save(new Group("Group 3"));
        };
    }
}
