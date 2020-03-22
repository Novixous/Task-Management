package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.mapper.GroupMapper;
import com.task.management.server.taskmanagementserver.model.request.GroupRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class GroupController {
    private final GroupMapper groupMapper;

    public GroupController(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

    @GetMapping("/groups")
    public HashMap<String, Object> getGroups() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("data", groupMapper.getAllGroups());
        return result;
    }

    @PostMapping("/group")
    public int createGroup(@RequestBody GroupRequest groupRequest) {
        return groupMapper.createGroup(groupRequest.getData());
    }
}
