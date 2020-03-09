package com.task.management.server.taskmanagementserver.mapper;

import com.task.management.server.taskmanagementserver.model.InitialValue;
import com.task.management.server.taskmanagementserver.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TaskMapper {


    @Select("SELECT id_task as taskId, " +
            "id_old_task as oldTaskId, " +
            "task_name as taskName, " +
            "create_date as createdTime, " +
            "deadline as deadline, " +
            "account_create as accountCreated, " +
            "assignee as assignee, " +
            "description as description, " +
            "resolution as resolution, " +
            "img_solution as imgResolutionUrl, " +
            "result as result, " +
            "start_date as startTime, " +
            "end_date as endDate, " +
            "comment_manager as managerComment, " +
            "mark as mark, " +
            "reviewer as reviewerId, " +
            "review_date as reviewTime, " +
            "confirm_id as confirmId, " +
            "approve_id as approvedId, " +
            "status_id as statusId, " +
            "group_id as groupId " +
            "FROM task WHERE ${fieldName} = #{value}")
    List<Task> getTasksByIdField(@Param("fieldName") String fieldName, @Param("value") Long value);


}
