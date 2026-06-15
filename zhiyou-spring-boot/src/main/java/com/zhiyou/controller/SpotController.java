package com.zhiyou.controller;

import com.zhiyou.common.R;
import com.zhiyou.model.Spot;
import com.zhiyou.service.SpotService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/spot")
public class SpotController {

    private final SpotService spotService;

    public SpotController(SpotService spotService) {
        this.spotService = spotService;
    }

    @GetMapping("/list")
    public R<List<Spot>> list() {
        return R.ok(spotService.getAllSpots());
    }

    @GetMapping("/{id}")
    public R<Spot> getById(@PathVariable Long id) {
        return R.ok(spotService.getSpotById(id));
    }

    @GetMapping("/search")
    public R<List<Spot>> search(@RequestParam String keyword) {
        return R.ok(spotService.searchSpots(keyword));
    }

    @PostMapping
    public R<Void> create(@RequestBody Spot spot) {
        boolean success = spotService.createSpot(spot);
        return success ? R.ok() : R.fail("创建失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody Spot spot) {
        boolean success = spotService.updateSpot(spot);
        return success ? R.ok() : R.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean success = spotService.deleteSpot(id);
        return success ? R.ok() : R.fail("删除失败");
    }
}
