package com.zhiyou.repository;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.zhiyou.config.AppProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class JsonDataRepository {

    private final String dataPath;

    public JsonDataRepository(AppProperties appProperties) {
        this.dataPath = appProperties.getDataPath();
        log.info("========== JsonDataRepository 初始化 ==========");
        log.info("从 AppProperties 获得数据目录: {}", dataPath);
        try {
            Path path = Paths.get(dataPath);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("[本地模式] 创建数据目录: {}", dataPath);
            } else {
                log.info("[本地模式] 数据目录: {}", dataPath);
            }
        } catch (IOException e) {
            log.error("创建数据目录失败", e);
        }
    }

    public <T> List<T> selectList(String fileName, Class<T> clazz) {
        try {
            Path filePath = Paths.get(dataPath, fileName);
            if (!Files.exists(filePath)) {
                return new ArrayList<>();
            }
            String content = Files.readString(filePath, StandardCharsets.UTF_8);
            return JSON.parseArray(content, clazz);
        } catch (IOException e) {
            log.error("读取数据失败: {}", fileName, e);
            return new ArrayList<>();
        }
    }

    public <T> T selectById(String fileName, Long id, Class<T> clazz) {
        List<T> list = selectList(fileName, clazz);
        return list.stream()
                .filter(item -> {
                    try {
                        Object itemId = getFieldValue(item, "id");
                        return itemId != null && itemId.toString().equals(id.toString());
                    } catch (Exception e) {
                        return false;
                    }
                })
                .findFirst()
                .orElse(null);
    }

    public <T> boolean insert(String fileName, T entity) {
        List<T> list = selectList(fileName, (Class<T>) entity.getClass());
        try {
            Long maxId = list.stream()
                    .map(item -> {
                        try {
                            Object id = getFieldValue(item, "id");
                            return id != null ? Long.parseLong(id.toString()) : 0L;
                        } catch (Exception e) {
                            return 0L;
                        }
                    })
                    .max(Long::compareTo)
                    .orElse(0L);
            setFieldValue(entity, "id", maxId + 1);
        } catch (Exception e) {
            log.error("生成 ID 失败", e);
            return false;
        }
        list.add(entity);
        return saveList(fileName, list);
    }

    public <T> boolean update(String fileName, T entity) {
        List<T> list = selectList(fileName, (Class<T>) entity.getClass());
        Object entityId = null;
        try {
            entityId = getFieldValue(entity, "id");
        } catch (Exception e) {
            log.error("获取 ID 失败", e);
            return false;
        }
        if (entityId == null) {
            return false;
        }
        boolean updated = false;
        for (int i = 0; i < list.size(); i++) {
            Object itemId = null;
            try {
                itemId = getFieldValue(list.get(i), "id");
            } catch (Exception e) {
                continue;
            }
            if (itemId != null && itemId.toString().equals(entityId.toString())) {
                try {
                    setFieldValue(entity, "id", Long.parseLong(itemId.toString()));
                } catch (Exception e) {
                    log.error("设置 ID 失败", e);
                    return false;
                }
                list.set(i, entity);
                updated = true;
                break;
            }
        }
        return updated && saveList(fileName, list);
    }

    public <T> boolean delete(String fileName, Long id, Class<T> clazz) {
        List<T> list = selectList(fileName, clazz);
        boolean removed = list.removeIf(item -> {
            try {
                Object itemId = getFieldValue(item, "id");
                return itemId != null && itemId.toString().equals(id.toString());
            } catch (Exception e) {
                return false;
            }
        });
        return removed && saveList(fileName, list);
    }

    public <T> List<T> selectListByField(String fileName, Class<T> clazz, String fieldName, Object value) {
        List<T> list = selectList(fileName, clazz);
        return list.stream()
                .filter(item -> {
                    try {
                        Object fieldValue = getFieldValue(item, fieldName);
                        return fieldValue != null && fieldValue.toString().equals(value.toString());
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    private <T> boolean saveList(String fileName, List<T> list) {
        try {
            Path filePath = Paths.get(dataPath, fileName);
            String json = JSON.toJSONString(list, JSONWriter.Feature.PrettyFormat);
            Files.writeString(filePath, json, StandardCharsets.UTF_8);
            return true;
        } catch (IOException e) {
            log.error("保存数据失败: {}", fileName, e);
            return false;
        }
    }

    private Object getFieldValue(Object obj, String fieldName) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(obj);
    }

    private void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }
}
