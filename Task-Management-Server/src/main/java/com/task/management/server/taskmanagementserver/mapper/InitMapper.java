package com.task.management.server.taskmanagementserver.mapper;

import com.task.management.server.taskmanagementserver.model.InitialValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface InitMapper {
    @Select("SELECT ${id} as id, content as content, content FROM ${table}")
    List<InitialValue> getInitValues(@Param("table") String table, @Param("id") String id);
}
