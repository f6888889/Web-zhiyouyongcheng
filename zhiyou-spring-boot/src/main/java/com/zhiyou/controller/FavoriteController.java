package com.zhiyou.controller;

import com.zhiyou.common.R;
import com.zhiyou.model.Favorite;
import com.zhiyou.service.FavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @GetMapping("/list")
    public R<List<Favorite>> list() {
        return R.ok(favoriteService.getAllFavorites());
    }

    @GetMapping("/user/{userId}")
    public R<List<Favorite>> getByUserId(@PathVariable Long userId) {
        return R.ok(favoriteService.getFavoritesByUserId(userId));
    }

    @GetMapping("/check")
    public R<Boolean> check(@RequestParam Long userId, @RequestParam Long spotId) {
        return R.ok(favoriteService.isFavorite(userId, spotId));
    }

    @PostMapping
    public R<Void> add(@RequestBody Favorite favorite) {
        boolean success = favoriteService.addFavorite(favorite);
        return success ? R.ok() : R.fail("收藏失败");
    }

    @DeleteMapping
    public R<Void> remove(@RequestParam Long userId, @RequestParam Long spotId) {
        boolean success = favoriteService.removeFavorite(userId, spotId);
        return success ? R.ok() : R.fail("取消收藏失败");
    }
}
