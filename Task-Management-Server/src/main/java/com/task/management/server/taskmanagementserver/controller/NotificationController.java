package com.task.management.server.taskmanagementserver.controller;


import com.task.management.server.taskmanagementserver.dto.NotificationRequestDto;
import com.task.management.server.taskmanagementserver.dto.SubscriptionRequestDto;
import com.task.management.server.taskmanagementserver.mapper.AccountMapper;
import com.task.management.server.taskmanagementserver.model.request.TokenRequestModel;
import com.task.management.server.taskmanagementserver.service.NotificationService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final AccountMapper accountMapper;

    public NotificationController(NotificationService notificationService, AccountMapper accountMapper) {
        this.notificationService = notificationService;
        this.accountMapper = accountMapper;
    }

    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        notificationService.unsubscribeFromTopic(subscriptionRequestDto);
    }

    @PostMapping("/token")
    public String sendPnsToDevice(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendPnsToDevice(notificationRequestDto);
    }

    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
        return notificationService.sendPnsToTopic(notificationRequestDto);
    }

    @PostMapping("/registerToken")
    public int registerToken(@RequestBody TokenRequestModel tokenRequestModel) {
        if (accountMapper.getTokenByTokenAndAccountId(tokenRequestModel.getAccountId(), tokenRequestModel.getToken()) == null) {
            return accountMapper.registerToken(tokenRequestModel.getAccountId(), tokenRequestModel.getToken());
        }
        return 0;
    }

    @PostMapping("/deleteToken")
    public int deleteToken(@RequestBody TokenRequestModel tokenRequestModel) {
        return accountMapper.deleteToken(tokenRequestModel.getAccountId(), tokenRequestModel.getToken());

    }
}
