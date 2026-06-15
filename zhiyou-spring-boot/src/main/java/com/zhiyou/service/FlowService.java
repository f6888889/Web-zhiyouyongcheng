package com.zhiyou.service;

import com.zhiyou.model.FlowData;
import com.zhiyou.repository.JsonDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FlowService {

    private final JsonDataRepository repository;

    public FlowService(JsonDataRepository repository) {
        this.repository = repository;
    }

    private static final String FILE_NAME = "flow.json";

    public List<FlowData> getAllFlowData() {
        return repository.selectList(FILE_NAME, FlowData.class);
    }

    public FlowData getFlowDataById(Long id) {
        return repository.selectById(FILE_NAME, id, FlowData.class);
    }

    public List<FlowData> getFlowDataBySpotId(Long spotId) {
        return repository.selectListByField(FILE_NAME, FlowData.class, "spotId", spotId);
    }

    public boolean createFlowData(FlowData flowData) {
        flowData.setUpdateTime(LocalDateTime.now());
        return repository.insert(FILE_NAME, flowData);
    }

    public boolean updateFlowData(FlowData flowData) {
        flowData.setUpdateTime(LocalDateTime.now());
        return repository.update(FILE_NAME, flowData);
    }

    public boolean deleteFlowData(Long id) {
        return repository.delete(FILE_NAME, id, FlowData.class);
    }
}
