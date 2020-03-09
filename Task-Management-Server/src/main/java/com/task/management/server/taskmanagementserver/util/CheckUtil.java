package com.task.management.server.taskmanagementserver.util;

import java.lang.reflect.Field;

public class CheckUtil {
    public static boolean hasFieldNotNull(Object object) {
        boolean result = false;
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.get(object) != null) result = true;
                break;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public static boolean hasFieldNotNull(Object object, String exceptionField) {
        boolean result = false;
        try {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (!field.getName().equals(exceptionField)) {
                    if (field.get(object) != null) result = true;
                    break;
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }
}
