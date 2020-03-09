package com.task.management.server.taskmanagementserver.mapper;

import com.task.management.server.taskmanagementserver.model.Account;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

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
            "group_idgroup as groupId, " +
            "deactivated as deactivated " +
            "FROM account where idaccount = #{id}")
    Account getAccountById(@Param("parameterValue") Long id);

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
            "group_idgroup as groupId, " +
            "deactivated as deactivated " +
            "FROM account where ${fieldName} = #{fieldValue}")
    List<Account> getAccountsByField(@Param("fieldName") String fieldName, @Param("fieldValue") Long value);


    @Insert("INSERT INTO account (username, password, firstname, lastname, phone, email, address, role_id, group_idgroup, deactivated)" +
            "VALUES(" +
            "#{account.username}, " +
            "#{account.password}, " +
            "#{account.firstName}, " +
            "#{account.lastName}, " +
//            "#{account.fullName}, " +
            "#{account.phone}, " +
            "#{account.email}, " +
            "#{account.address}, " +
            "#{account.roleId}, " +
            "#{account.groupId}," +
            "#{account.deactivated}" +
            ")")
    int CreateAccount(@Param("account") Account account);

    @Update({
            "<script>" +
                    "UPDATE account set " +
                    "<if test='account.username != null'>" +
                    "username = #{account.username}" +
                    "<if test='account.password !=null or account.firstName !=null or account.lastName !=null or account.fullName !=null or account.phone !=null or account.email !=null or account.address !=null or account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.password != null'>" +
                    "password = #{account.password}" +
                    "<if test='account.firstName !=null or account.lastName !=null or account.fullName !=null or account.phone !=null or account.email !=null or account.address !=null or account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.firstName != null'>" +
                    "firstname = #{account.firstName}" +
                    "<if test='account.lastName !=null or account.fullName !=null or account.phone !=null or account.email !=null or account.address !=null or account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.lastName != null'>" +
                    "lastname = #{account.lastName}" +
                    "<if test='account.fullName !=null or account.phone !=null or account.email !=null or account.address !=null or account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.fullName != null'>" +
                    "fullname = #{account.fullName}" +
                    "<if test='account.phone !=null or account.email !=null or account.address !=null or account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.phone != null'>" +
                    "phone = #{account.phone}" +
                    "<if test='account.email !=null or account.address !=null or account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.email != null'>" +
                    "email = #{account.email}" +
                    "<if test='account.address !=null or account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.address != null'>" +
                    "address = #{account.address}" +
                    "<if test='account.roleId !=null or account.groupId !=null or account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.roleId != null'>" +
                    "role_id = #{account.roleId}" +
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
                    "WHERE idaccount = #{account.accountId}" +
                    "</script>"})
    int UpdateAccountById(@Param("account") Account account);

}
