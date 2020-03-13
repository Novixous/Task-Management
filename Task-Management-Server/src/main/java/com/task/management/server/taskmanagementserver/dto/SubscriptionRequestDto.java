package com.task.management.server.taskmanagementserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionRequestDto {

    String topicName;
    List<String> tokens;

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }
}
