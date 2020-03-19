package com.task.management.server.taskmanagementserver.mapper;

import com.task.management.server.taskmanagementserver.model.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface GroupMapper {

    @Select("SELECT idgroup as groupId, groupName as groupName, description as description, idAuthor as creator FROM task_management.group")
    List<Group> getAllGroups();
}
