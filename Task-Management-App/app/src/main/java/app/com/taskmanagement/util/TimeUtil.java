package app.com.taskmanagement.util;


import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import app.com.taskmanagement.model.TaskModel;
import app.com.taskmanagement.model.response.TaskResponse;

public class TimeUtil {
    public static List<TaskResponse> convertTaskToTaskResponse(List<TaskModel> original) {
        List<TaskResponse> result = new ArrayList<>();
        try {
            for (TaskModel task : original) {
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

    public static List<TaskModel> convertTaskResponseToTask(List<TaskResponse> responses) {
        List<TaskModel> result = new ArrayList<>();
        try {
            for (TaskResponse taskResponse : responses) {
                Field[] responseFields = taskResponse.getClass().getDeclaredFields();
                TaskModel originalTask = new TaskModel();
                for (int i = 0; i < responseFields.length; i++) {
                    Field responseField = responseFields[i];
                    responseField.setAccessible(true);
                    Field[] originalTaskFields = originalTask.getClass().getDeclaredFields();
                    Field originalTaskField = originalTaskFields[i];
                    originalTaskField.setAccessible(true);
                    if (responseField.get(taskResponse) != null) {
                        if (originalTaskField.getType().isAssignableFrom(Instant.class)) {
                            originalTaskField.set(originalTask, Instant.parse(responseField.get(taskResponse).toString()));
                        } else {
                            originalTaskField.set(originalTask, responseField.get(taskResponse));
                        }
                    }
                }
                result.add(originalTask);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

}
