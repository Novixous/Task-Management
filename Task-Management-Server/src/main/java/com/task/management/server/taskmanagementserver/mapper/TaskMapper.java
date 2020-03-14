package com.task.management.server.taskmanagementserver.mapper;

import com.task.management.server.taskmanagementserver.model.InitialValue;
import com.task.management.server.taskmanagementserver.model.Task;
import org.apache.ibatis.annotations.*;
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
            "approve_id as approvedId, " +
            "status_id as status, " +
            "group_id as groupId, " +
            "edited_by as editedBy, " +
            "edited_at as editedAt, " +
            "closed as closed " +
            "FROM task WHERE ${fieldName} = #{value}")
    List<Task> getTasksByIdField(@Param("fieldName") String fieldName, @Param("value") Long value);

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
            "approve_id as approvedId, " +
            "status_id as status, " +
            "group_id as groupId " +
            "edited_by as editedBy, " +
            "edited_at as editedAt, " +
            "closed as closed " +
            "FROM task WHERE ${fieldName} = #{value} AND status_id = #{approveStatus}")
    List<Task> getTasksByIdFieldAndStatus(@Param("fieldName") String fieldName, @Param("value") Long value,
                                          @Param("approveStatus") Long approveStatus);

    List<Task> getUnfinishedAndUnNotifiedTasks();

    @Insert("INSERT INTO task " +
            "(id_old_task, " +
            "task_name, " +
            "create_date, " +
            "deadline, " +
            "account_create, " +
            "assignee, " +
            "description, " +
            "resolution, " +
            "img_solution, " +
            "result, " +
            "start_date, " +
            "end_date, " +
            "comment_manager, " +
            "mark, " +
            "reviewer, " +
            "review_date, " +
            "status_id, " +
            "approve_id, " +
            "group_id, " +
            "edited_by, " +
            "edited_at" +
            ") VALUES (" +
            "#{task.oldTaskId}, " +
            "#{task.taskName}, " +
            "#{task.createdTime}, " +
            "#{task.deadline}, " +
            "#{task.accountCreated}, " +
            "#{task.assignee}, " +
            "#{task.description}, " +
            "#{task.resolution}, " +
            "#{task.imgResolutionUrl}, " +
            "#{task.result}, " +
            "#{task.startTime}, " +
            "#{task.endTime}, " +
            "#{task.managerComment}, " +
            "#{task.mark}, " +
            "#{task.reviewerId}, " +
            "#{task.reviewTime}, " +
            "#{task.status}, " +
            "#{task.approvedId}, " +
            "#{task.groupId}, " +
            "#{task.editedBy}, " +
            "#{task.editedAt}" +
            ")")
    int createTask(@Param("task") Task task);

    @Update({
            "<script>" +
                    "UPDATE task SET " +
                    "<if test='task.oldTaskId != null'>" +
                    "id_old_task = #{task.oldTaskId}" +
                    "<if test='task.taskName != null or task.createdTime !=null or task.deadline !=null or task.accountCreated !=null or task.assignee !=null or task.description !=null or task.resolution !=null or task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.taskName != null'>" +
                    "task_name = #{task.taskName}" +
                    "<if test='task.createdTime !=null or task.deadline !=null or task.accountCreated !=null or task.assignee !=null or task.description !=null or task.resolution !=null or task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.createdTime != null'>" +
                    "create_date = #{task.createdTime}" +
                    "<if test='task.deadline !=null or task.accountCreated !=null or task.assignee !=null or task.description !=null or task.resolution !=null or task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.deadline != null'>" +
                    "deadline = #{task.deadline}" +
                    "<if test='task.accountCreated !=null or task.assignee !=null or task.description !=null or task.resolution !=null or task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.accountCreated != null'>" +
                    "account_create = #{task.accountCreated}" +
                    "<if test='task.assignee !=null or task.description !=null or task.resolution !=null or task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.assignee != null'>" +
                    "assignee = #{task.assignee}" +
                    "<if test='task.description !=null or task.resolution !=null or task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.description != null'>" +
                    "description = #{task.description}" +
                    "<if test='task.resolution !=null or task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.resolution != null'>" +
                    "resolution = #{task.resolution}" +
                    "<if test='task.imgResolutionUrl !=null or task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.imgResolutionUrl != null'>" +
                    "img_solution = #{task.imgResolutionUrl}" +
                    "<if test='task.result !=null or task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.result != null'>" +
                    "result = #{task.result}" +
                    "<if test='task.startTime !=null or task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.startTime != null'>" +
                    "start_date = #{task.startTime}" +
                    "<if test='task.endTime !=null or task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.endTime != null'>" +
                    "end_date = #{task.endTime}" +
                    "<if test='task.managerComment !=null or task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.managerComment != null'>" +
                    "comment_manager = #{task.managerComment}" +
                    "<if test='task.mark !=null or task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.mark != null'>" +
                    "mark = #{task.mark}" +
                    "<if test='task.reviewerId !=null or task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.reviewerId != null'>" +
                    "reviewer = #{task.reviewerId}" +
                    "<if test='task.reviewTime !=null or task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.reviewTime != null'>" +
                    "review_date = #{task.reviewTime}" +
                    "<if test='task.status !=null or task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.status != null'>" +
                    "status_id = #{task.status}" +
                    "<if test='task.approvedId !=null or task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.approvedId != null'>" +
                    "approve_id = #{task.approvedId}" +
                    "<if test='task.groupId !=null or task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.groupId != null'>" +
                    "group_id = #{task.groupId}" +
                    "<if test='task.editedBy !=null or task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.editedBy != null'>" +
                    "edited_by = #{task.editedBy}" +
                    "<if test='task.editedAt !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.editedAt != null'>" +
                    "edited_at = #{task.editedAt}" +
                    "<if test='task.closed !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='task.closed != null'>" +
                    "closed = #{task.closed}" +
                    "</if>" +
                    " WHERE id_task = #{task.taskId}" +
                    "</script>"})
    int updateTask(@Param("task") Task task);


}
