package com.zhiyou.service;

import com.zhiyou.model.Favorite;
import com.zhiyou.repository.JsonDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class FavoriteService {

    private final JsonDataRepository repository;

    public FavoriteService(JsonDataRepository repository) {
        this.repository = repository;
    }

    private static final String FILE_NAME = "favorite.json";

    public List<Favorite> getAllFavorites() {
        return repository.selectList(FILE_NAME, Favorite.class);
    }

    public List<Favorite> getFavoritesByUserId(Long userId) {
        return repository.selectListByField(FILE_NAME, Favorite.class, "userId", userId);
    }

    public boolean isFavorite(Long userId, Long spotId) {
        List<Favorite> favorites = repository.selectList(FILE_NAME, Favorite.class);
        return favorites.stream()
                .anyMatch(f -> f.getUserId().equals(userId) && f.getSpotId().equals(spotId));
    }

    public boolean addFavorite(Favorite favorite) {
        favorite.setCreateTime(LocalDateTime.now());
        return repository.insert(FILE_NAME, favorite);
    }

    public boolean removeFavorite(Long userId, Long spotId) {
        List<Favorite> favorites = repository.selectList(FILE_NAME, Favorite.class);
        Long removeId = null;
        for (Favorite f : favorites) {
            if (f.getUserId().equals(userId) && f.getSpotId().equals(spotId)) {
                removeId = f.getId();
                break;
            }
        }
        if (removeId != null) {
            return repository.delete(FILE_NAME, removeId, Favorite.class);
        }
        return false;
    }
}
