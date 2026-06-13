package com.zhiyou.service;

import com.zhiyou.model.Spot;
import com.zhiyou.repository.JsonDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class SpotService {

    private final JsonDataRepository repository;

    public SpotService(JsonDataRepository repository) {
        this.repository = repository;
    }

    private static final String FILE_NAME = "spot.json";

    public List<Spot> getAllSpots() {
        return repository.selectList(FILE_NAME, Spot.class);
    }

    public Spot getSpotById(Long id) {
        return repository.selectById(FILE_NAME, id, Spot.class);
    }

    public List<Spot> getSpotsByStatus(Integer status) {
        return repository.selectListByField(FILE_NAME, Spot.class, "status", status);
    }

    public boolean createSpot(Spot spot) {
        spot.setCreateTime(LocalDateTime.now());
        spot.setUpdateTime(LocalDateTime.now());
        return repository.insert(FILE_NAME, spot);
    }

    public boolean updateSpot(Spot spot) {
        spot.setUpdateTime(LocalDateTime.now());
        return repository.update(FILE_NAME, spot);
    }

    public boolean deleteSpot(Long id) {
        return repository.delete(FILE_NAME, id, Spot.class);
    }

    public List<Spot> searchSpots(String keyword) {
        List<Spot> allSpots = getAllSpots();
        if (keyword == null || keyword.trim().isEmpty()) {
            return allSpots;
        }
        String lowerKeyword = keyword.toLowerCase();
        return allSpots.stream()
                .filter(spot -> {
                    if (spot.getName() != null && spot.getName().toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    if (spot.getDescription() != null && spot.getDescription().toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    if (spot.getLocation() != null && spot.getLocation().toLowerCase().contains(lowerKeyword)) {
                        return true;
                    }
                    return false;
                })
                .toList();
    }
}
