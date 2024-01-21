package org.example.attendancelist.controller;

import org.example.attendancelist.model.Group;
import org.example.attendancelist.repository.GroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/groups")
public class GroupController {

    private final GroupRepository groupRepository;

    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @PostMapping(path="")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup(@RequestBody Group group) {
        groupRepository.save(group);
    }

    @GetMapping(path="")
    public Iterable<Group> getGroups() {
        return groupRepository.findAll();
    }
}