package org.example.attendancelist.controller;

import org.example.attendancelist.model.Group;
import org.example.attendancelist.repository.GroupRepository;
import org.example.attendancelist.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/groups")
public class GroupController {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    public GroupController(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping(path="")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup(@RequestBody Group group) {
        groupRepository.save(group);
    }

    @GetMapping(path="")
    public Iterable<Group> getGroups(@RequestParam(required = false) String name, @RequestParam(required = false) Integer id) {
        if (id != null) {
            Optional<Group> group = groupRepository.findById(id);
            return group.map(Collections::singletonList).orElse(Collections.emptyList());
        }  else if (name != null)
            return groupRepository.findByName(name);

        return groupRepository.findAll();
    }

    @DeleteMapping(path="")
    public void deleteGroup(@RequestParam Integer id) {
        studentRepository.updateGroupIdToNull(id);
        groupRepository.deleteById(id);
    }
}