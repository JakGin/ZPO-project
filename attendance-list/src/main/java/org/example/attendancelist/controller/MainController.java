package org.example.attendancelist.controller;

import jakarta.transaction.Transactional;
import org.example.attendancelist.model.Group;
import org.example.attendancelist.repository.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api")
public class MainController {

    private final GroupRepository groupRepository;

    public MainController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @PostMapping(path="/groups")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup() {
//        groupRepository.save(group);
        System.out.println("here");
    }

    @GetMapping(path="/groups")
    public Iterable<Group> getGroups() {
        return groupRepository.findAll();
    }
}
