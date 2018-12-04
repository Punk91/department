package ru.example.department.service.core.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.example.department.model.core.entity.LogEntity;
import ru.example.department.repository.core.LogRepo;

import java.util.Calendar;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class LogChangesImpl implements LogChanges {

    private ObjectMapper objectMapper;
    private LogRepo logRepo;
    private JsonParser jsonParser;

    public LogChangesImpl(ObjectMapper objectMapper, LogRepo logRepo, JsonParser jsonParser) {
        this.objectMapper = objectMapper;
        this.logRepo = logRepo;
        this.jsonParser = jsonParser;
    }

    @Override
    public void writeChangeLog(String entityName, String actionType, String username, Object oldObject, Object newObject) {
        StringBuilder oldChangedValues = new StringBuilder();
        StringBuilder newChangedValues = new StringBuilder();

        String oldObjStr = this.convertObjToJsonString(oldObject);
        String newObjStr = this.convertObjToJsonString(newObject);

        LogEntity logEntity = new LogEntity();
        logEntity.setActionTime(Calendar.getInstance().getTime());
        logEntity.setActionType(actionType);
        logEntity.setEntityName(entityName);
        logEntity.setLogin(username);

        if ((oldObjStr != null) && (newObjStr != null)) {
            try {
                JsonObject oldObjJson = jsonParser.parse(oldObjStr).getAsJsonObject();
                JsonObject newObjJson = jsonParser.parse(newObjStr).getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entrySet = oldObjJson.entrySet();
                for (Map.Entry<String, JsonElement> entry : entrySet) {
                    this.writeChangedValues(entry, newObjJson, oldChangedValues, newChangedValues);
                }
            } catch (IllegalStateException e) {
                log.error("Write log: transform object to json exception", e);
            }
        } else if (oldObjStr != null) {
            this.formatJson(oldChangedValues, oldObjStr);
        } else {
            this.formatJson(newChangedValues, newObjStr);
        }

        if (oldChangedValues.toString().isEmpty() && newChangedValues.toString().isEmpty()) {
            return;
        }
        logEntity.setOldObject(oldChangedValues.toString().getBytes());
        logEntity.setNewObject(newChangedValues.toString().getBytes());
        logRepo.saveAndFlush(logEntity);
    }

    @Override
    public void writeChangeLog(String entityName, String actionType, String username) {
        LogEntity logEntity = new LogEntity();
        logEntity.setActionTime(Calendar.getInstance().getTime());
        logEntity.setActionType(actionType);
        logEntity.setEntityName(entityName);
        logEntity.setLogin(username);
        logEntity.setOldObject("".getBytes());
        logEntity.setNewObject("".getBytes());
        logRepo.saveAndFlush(logEntity);
    }

    @Override
    public String convertObjToJsonString(Object object) {
        String result = "";
        try {
            result = objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("object to json string serialize error", e);
        }
        return result;
    }

    private void writeChangedValues(Map.Entry<String, JsonElement> entry, JsonObject newObjJson, StringBuilder oldChangedValues, StringBuilder newChangedValues) {
        try {
            String key = entry.getKey();
            String curOldElem = "";
            String curNewElem = "";
            if (entry.getValue() != null) {
                curOldElem = this.getJsonElementAsString(entry.getValue());
            }
            JsonElement newJsonElement = newObjJson.get(key);
            if (newJsonElement != null) {
                curNewElem = this.getJsonElementAsString(newJsonElement);
            }
            if (!curOldElem.equals(curNewElem) || "id".equals(key)) {
                oldChangedValues.append(key).append(" = '").append(curOldElem).append("'").append("\n");
                newChangedValues.append(key).append(" = '").append(curNewElem).append("'").append("\n");
            }
        } catch (UnsupportedOperationException e) {
            log.error("Unsupported operation exception");
        }
    }

    private StringBuilder formatJson(StringBuilder stringBuilder, String jsonString) {
        try {
            JsonObject objJson = jsonParser.parse(jsonString).getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = objJson.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String curElem = this.getJsonElementAsString(entry.getValue());
                stringBuilder.append(entry.getKey()).append(" = '").append(curElem).append("'").append("\n");
            }
        } catch (IllegalStateException e) {
            log.error("Write log: transform object to gson exception", e);
        }
        return stringBuilder;
    }

    private String getJsonElementAsString(JsonElement jsonElement) {
        String element = "null";
        if (jsonElement.isJsonArray()) {
            element = jsonElement.getAsJsonArray().toString();
        } else if (jsonElement.isJsonObject()) {
            element = jsonElement.getAsJsonObject().toString();
        } else if (!jsonElement.isJsonNull()) {
            element = jsonElement.getAsString();
        }
        return element;
    }

}
