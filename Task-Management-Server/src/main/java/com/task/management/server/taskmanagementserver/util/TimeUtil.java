package com.task.management.server.taskmanagementserver.util;

import com.task.management.server.taskmanagementserver.model.Task;
import com.task.management.server.taskmanagementserver.model.response.TaskResponse;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TimeUtil {
    public static List<TaskResponse> convertTaskToTaskResponse(List<Task> original) {
        List<TaskResponse> result = new ArrayList<>();
//        DateTimeFormatter formatter =
//                DateTimeFormatter.ofPattern("dd-MM-yyyy:HH:mm:ss")
//                        .withLocale(Locale.US)
//                        .withZone(ZoneId.of(timeonze));
        try {
            for (Task task : original) {
                Field[] fields = task.getClass().getDeclaredFields();
                TaskResponse response = new TaskResponse();
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    Field[] responseFields = response.getClass().getDeclaredFields();
                    Field responseField = responseFields[i];
                    responseField.setAccessible(true);
                    if (field.get(task) != null) {
                        if (field.getType().isAssignableFrom(Instant.class)) {
                            Instant value = (Instant) field.get(task);
                            responseField.set(response, value.toString());
                        } else {
                            responseField.set(response, field.get(task));
                        }
                    }
                }
                result.add(response);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

}
