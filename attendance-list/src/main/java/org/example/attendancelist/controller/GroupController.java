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
        if (name != null && id != null)
            return groupRepository.findByIdAndName(id, name);
        else if (name != null)
            return groupRepository.findByName(name);
        else if (id != null) {
            Optional<Group> group = groupRepository.findById(id);
            return group.map(Collections::singletonList).orElse(Collections.emptyList());
        }
        return groupRepository.findAll();
    }

    @DeleteMapping(path="")
    public void deleteGroup(@RequestParam Integer id) {
        studentRepository.updateGroupIdToNull(id);
        groupRepository.deleteById(id);
    }
}