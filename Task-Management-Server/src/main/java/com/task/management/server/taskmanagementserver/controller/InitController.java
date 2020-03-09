package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.mapper.InitMapper;
import com.task.management.server.taskmanagementserver.model.InitialValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class InitController {
    private final InitMapper initMapper;

    public InitController(InitMapper initMapper) {
        this.initMapper = initMapper;
    }

    @GetMapping("/getInitialValue")
    public HashMap<String, List<InitialValue>> getInitialValue() {
        HashMap<String, List<InitialValue>> result = new HashMap<>();
        result.put("status", initMapper.getInitValues("status", "id_status"));
        result.put("confirm", initMapper.getInitValues("confirm", "id_confirm"));
        result.put("approve", initMapper.getInitValues("approve", "id_approve"));
        result.put("role", initMapper.getInitValues("role", "idrole"));
        return result;
    }
}
