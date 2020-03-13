package com.task.management.server.taskmanagementserver.controller;


import com.task.management.server.taskmanagementserver.dto.NotificationRequestDto;
import com.task.management.server.taskmanagementserver.dto.SubscriptionRequestDto;
import com.task.management.server.taskmanagementserver.mapper.AccountMapper;
import com.task.management.server.taskmanagementserver.model.request.TokenRequestModel;
import com.task.management.server.taskmanagementserver.service.NotificationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void registerToken(@RequestBody TokenRequestModel tokenRequestModel) {
        accountMapper.registerToken(tokenRequestModel.getAccountId(), tokenRequestModel.getToken());
    }
}
