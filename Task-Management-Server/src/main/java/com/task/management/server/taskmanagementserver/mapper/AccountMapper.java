package com.task.management.server.taskmanagementserver.mapper;

import com.task.management.server.taskmanagementserver.model.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountMapper {

    @Select("SELECT idaccount as accountId, " +
            "username as username, " +
            "password as password, " +
            "firstname as firstName, " +
            "lastname as lastName, " +
            "fullname as fullName, " +
            "phone as phone, " +
            "email as email, " +
            "address as address, " +
            "role_id as roleId, " +
            "group_idgroup as groupId " +
            "FROM account where ${parameter} = #{parameterValue}")
    Account getAccountByParameter(@Param("parameter") String parameter,
                                  @Param("parameterValue") String parameterValue);

    @Insert("INSERT INTO account VALUES(username, password, firstname, lastname, fullname, phone, email, address, role_id, group_idgroup, deactivated)" +
            "VALUES(" +
            "#{account.usernamme}, " +
            "#{account.password}, " +
            "#{account.firstname}, " +
            "#{account.lastname}, " +
            "#{account.fullname}, " +
            "#{account.phone}, " +
            "#{account.email}, " +
            "#{account.address}, " +
            "#{account.role_id}, " +
            "#{account.groupId}," +
            "#{account.deactivated}" +
            ")")
    int CreateAccount(@Param("account") Account account);

    @Update({"UPDATE account set " +
            "<if test='account.username != null'>" +
            "username = #{account.usernamme}" +
            "<if test='account.password !=null or account.firstname !=null or account.lastname !=null or account.fullname !=null or account.phone !=null or account.email !=null or account.address !=null or account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.password != null'>" +
            "password = #{account.password}" +
            "<if test='account.firstname !=null or account.lastname !=null or account.fullname !=null or account.phone !=null or account.email !=null or account.address !=null or account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.firstname != null'>" +
            "firstname = #{account.firstname}" +
            "<if test='account.lastname !=null or account.fullname !=null or account.phone !=null or account.email !=null or account.address !=null or account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.lastname != null'>" +
            "lastname = #{account.lastname}" +
            "<if test='account.fullname !=null or account.phone !=null or account.email !=null or account.address !=null or account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.fullname != null'>" +
            "fullname = #{account.fullname}" +
            "<if test='account.phone !=null or account.email !=null or account.address !=null or account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.phone != null'>" +
            "phone = #{account.phone}" +
            "<if test='account.email !=null or account.address !=null or account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.email != null'>" +
            "email = #{account.email}" +
            "<if test='account.address !=null or account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.address != null'>" +
            "address = #{account.address}" +
            "<if test='account.role_id !=null or account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.role_id != null'>" +
            "role_id = #{account.role_id}" +
            "<if test='account.groupId !=null or account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.groupId != null'>" +
            "group_idgroup = #{account.groupId}" +
            "<if test='account.deactivated !=null'>" +
            ", " +
            "</if>" +
            "</if>" +
            "<if test='account.groupId != null'>" +
            "deactivated = #{account.deactivated}" +
            "</if>" +
            "WHERE idaccount = #{account.accountId"})
    int UpdateAccountById(@Param("account") Account account);

}
