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
            "firstname as firstName, " +
            "lastname as lastName, " +
            "fullname as fullName, " +
            "phone as phone, " +
            "email as email, " +
            "address as address, " +
            "role_id as roleId, " +
            "group_id as groupId, " +
            "deactivated as deactivated " +
            "FROM account where username = #{username} and password = #{password}")
    Account login(@Param("username") String username, @Param("password") String password);

    @Select("SELECT idaccount as accountId, " +
            "username as username, " +
//            "password as password, " +
            "firstname as firstName, " +
            "lastname as lastName, " +
            "fullname as fullName, " +
            "phone as phone, " +
            "email as email, " +
            "address as address, " +
            "role_id as roleId, " +
            "group_id as groupId, " +
            "deactivated as deactivated " +
            "FROM account where idaccount = #{id}")
    Account getAccountById(@Param("id") Long id);

    @Select("SELECT idaccount as accountId, " +
            "username as username, " +
//            "password as password, " +
            "firstname as firstName, " +
            "lastname as lastName, " +
            "fullname as fullName, " +
            "phone as phone, " +
            "email as email, " +
            "address as address, " +
            "role_id as roleId, " +
            "group_id as groupId, " +
            "deactivated as deactivated " +
            "FROM account where username = #{username}")
    Account getAccountByUsername(@Param("username") String username);

    @Select({
            "<script>" +
                    "SELECT idaccount as accountId, " +
                    "username as username, " +
                    "password as password, " +
                    "firstname as firstName, " +
                    "lastname as lastName, " +
                    "fullname as fullName, " +
                    "phone as phone, " +
                    "email as email, " +
                    "address as address, " +
                    "role_id as roleId, " +
                    "group_id as groupId, " +
                    "deactivated as deactivated " +
                    "FROM account " +
                    "<if test='fieldName != null and fieldValue != null'>" +
                    "where ${fieldName} = #{fieldValue}" +
                    "</if>" +
                    "</script>"})
    List<Account> getAccountsByField(@Param("fieldName") String fieldName, @Param("fieldValue") Long value);


    @Insert("INSERT INTO account (username, password, firstname, lastname, fullname, phone, email, address, role_id, group_id, deactivated)" +
            "VALUES(" +
            "#{account.username}, " +
            "#{account.password}, " +
            "#{account.firstName}, " +
            "#{account.lastName}, " +
            "#{account.fullName}, " +
            "#{account.phone}, " +
            "#{account.email}, " +
            "#{account.address}, " +
            "#{account.roleId}, " +
            "#{account.groupId}," +
            "#{account.deactivated}" +
            ")")
    int createAccount(@Param("account") Account account);

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
                    "group_id = #{account.groupId}" +
                    "<if test='account.deactivated !=null'>" +
                    ", " +
                    "</if>" +
                    "</if>" +
                    "<if test='account.groupId != null'>" +
                    "deactivated = #{account.deactivated}" +
                    "</if>" +
                    "WHERE idaccount = #{account.accountId}" +
                    "</script>"})
    int updateAccountById(@Param("account") Account account);

    @Insert("INSERT INTO token (deviceToken, accountId) VALUES (#{token}, #{accountId})")
    int registerToken(@Param("accountId") Long accountId, @Param("token") String token);

    @Delete("DELETE FROM token WHERE deviceToken = #{token} AND accountId = #{accountId}")
    int deleteToken(@Param("accountId") Long accountId, @Param("token") String token);

    @Select("SELECT deviceToken FROM token WHERE deviceToken = #{token} AND accountId = #{accountId}")
    String getTokenByTokenAndAccountId(@Param("accountId") Long accountId, @Param("token") String token);

    @Select("SELECT deviceToken from token WHERE accountId = #{accountId}")
    List<String> getTokensByAccountId(@Param("accountId") Long accountId);

    @Select("SELECT role_id FROM account WHERE idaccount  = #{accountId}")
    Long getRoleIdByAccountId(@Param("accountId") Long accountId);

    @Select("SELECT idaccount FROM account WHERE group_id = #{groupId} AND role_id = 1")
    Long getManagerIdOfGroupByGroupId(@Param("groupId") Long groupId);


}
