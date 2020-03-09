package com.task.management.server.taskmanagementserver.controller;

import com.task.management.server.taskmanagementserver.mapper.AccountMapper;
import com.task.management.server.taskmanagementserver.model.Account;
import com.task.management.server.taskmanagementserver.util.CheckUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

@RestController
public class AccountController {
    final
    AccountMapper accountMapper;

    public AccountController(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    /**
     * get account by field name and field value
     *
     * @param parameter
     * @param parameterValue
     * @return
     */
    @GetMapping("/account")
    public Account getAccountByParameter(@RequestParam("parameter") String parameter,
                                         @RequestParam("parameterValue") String parameterValue) {
        return accountMapper.getAccountByParameter(parameter, parameterValue);
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
                accountMapper.UpdateAccountById(account);
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
        accountMapper.CreateAccount(account);
    }

    @Delete("/account")
    public void deleteAccount(@RequestParam Long accountId) {
        Account account = new Account();
        account.setAccountId(accountId);
        account.setDeactivated(true);
        accountMapper.UpdateAccountById(account);
    }
}
