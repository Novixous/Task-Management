package com.task.management.server.taskmanagementserver.mapper;

import com.task.management.server.taskmanagementserver.model.Group;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface GroupMapper {

    @Select("SELECT idgroup as groupId, groupName as groupName, description as description, idAuthor as creator FROM task_management.group")
    List<Group> getAllGroups();

    @Insert("INSERT INTO task_management.group (groupName, description, idAuthor) VALUES (#{group.groupName}, #{group.description}, #{group.creator})")
    int createGroup(@Param("group") Group group);
}
