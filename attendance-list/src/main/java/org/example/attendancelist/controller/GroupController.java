package org.example.attendancelist.controller;

import org.example.attendancelist.model.Group;
import org.example.attendancelist.repository.GroupRepository;
import org.example.attendancelist.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

/**
 * Controller class for managing Group entities.
 */
@RestController
@RequestMapping(path = "/api/groups")
public class GroupController {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    /**
     * Constructor for GroupController.
     *
     * @param groupRepository    Repository for managing Group entities.
     * @param studentRepository  Repository for managing Student entities.
     */
    public GroupController(GroupRepository groupRepository, StudentRepository studentRepository) {
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
    }

    /**
     * Endpoint for creating a new Group.
     *
     * @param group  The Group entity to be created.
     */
    @PostMapping(path = "")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createGroup(@RequestBody Group group) {
        groupRepository.save(group);
    }

    /**
     * Endpoint for retrieving groups based on optional query parameters.
     *
     * @param name  Optional parameter for filtering groups by name.
     * @param id    Optional parameter for retrieving a group by ID.
     * @return      A collection of groups based on the provided parameters.
     */
    @GetMapping(path = "")
    public Iterable<Group> getGroups(@RequestParam(required = false) String name, @RequestParam(required = false) Integer id) {
        if (id != null) {
            Optional<Group> group = groupRepository.findById(id);
            return group.map(Collections::singletonList).orElse(Collections.emptyList());
        } else if (name != null) {
            return groupRepository.findByName(name);
        }

        return groupRepository.findAll();
    }

    /**
     * Endpoint for deleting a group by ID.
     *
     * @param id  The ID of the group to be deleted.
     */
    @DeleteMapping(path = "")
    public void deleteGroup(@RequestParam Integer id) {
        studentRepository.updateGroupIdToNull(id);
        groupRepository.deleteById(id);
    }
}