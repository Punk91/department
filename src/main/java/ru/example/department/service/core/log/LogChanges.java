package ru.example.department.service.core.log;

public interface LogChanges {

    void writeChangeLog(String entityName, String actionType, String username, Object oldObject, Object newObject);

    void writeChangeLog(String entityName, String actionType, String username);

    String convertObjToJsonString(Object object);

}
