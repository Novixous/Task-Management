package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.mapper.AccountMapper;
import com.task.management.server.taskmanagementserver.model.Account;
import com.task.management.server.taskmanagementserver.model.request.LoginRequest;
import com.task.management.server.taskmanagementserver.util.CheckUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class AccountController {
    final
    AccountMapper accountMapper;

    public AccountController(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    /**
     * get account by id
     *
     * @param id
     * @return
     */
    @GetMapping("/account")
    public HashMap<String, Object> getAccountByParameter(@RequestParam("id") Long id) {
        HashMap<String, Object> result = new HashMap<>();
        Account account = accountMapper.getAccountById(id);
        if (account != null) {
            result.put("errorMessage", null);
            result.put("data", account);
        }
        return result;
    }

    /**
     * getAccounts by field name and field value
     *
     * @param fieldName
     * @param fieldValue
     * @return
     */
    @GetMapping("/accounts")
    public HashMap<String, List<Account>> getAccountsByField(@RequestParam(name = "fieldName", required = false) String fieldName,
                                                             @RequestParam(name = "fieldValue", required = false) Long fieldValue) {
        HashMap<String, List<Account>> result = new HashMap<>();
        result.put("data", accountMapper.getAccountsByField(fieldName, fieldValue));
        return result;
    }

    /**
     * update account
     *
     * @param account
     */
    @PutMapping("/account")
    public void updateAccount(@RequestBody Account account) {
        if (account.getAccountId() != null) {
            if (CheckUtil.hasFieldNotNull(account, "accountId")) {
                accountMapper.updateAccountById(account);
            }
        }
    }

    /**
     * create account
     *
     * @param account
     */
    @PostMapping("/account")
    public void createAccount(@RequestBody Account account) {
        accountMapper.createAccount(account);
    }

    /**
     * delete account
     *
     * @param accountId
     */
    @Delete("/account")
    public void deleteAccount(@RequestParam Long accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setDeactivated(true);
        accountMapper.updateAccountById(account);
    }

    /**
     * login
     *
     * @param loginRequest
     * @return
     */
    @PostMapping("/login")
    public HashMap<String, Object> login(@RequestBody LoginRequest loginRequest) {
        HashMap<String, Object> result = new HashMap<>();
        Account account = accountMapper.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (account != null) {
            result.put("errorMessage", null);
            result.put("data", account);
        } else {
            result.put("errorMessage", "Incorrect username or password");
            result.put("data", null);
        }
        return result;
    }


}
