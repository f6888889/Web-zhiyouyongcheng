package com.zhiyou.controller;

import com.zhiyou.common.R;
import com.zhiyou.model.FlowData;
import com.zhiyou.service.FlowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flow")
public class FlowController {

    private final FlowService flowService;

    public FlowController(FlowService flowService) {
        this.flowService = flowService;
    }

    @GetMapping("/list")
    public R<List<FlowData>> list() {
        return R.ok(flowService.getAllFlowData());
    }

    @GetMapping("/spot/{spotId}")
    public R<List<FlowData>> getBySpotId(@PathVariable Long spotId) {
        return R.ok(flowService.getFlowDataBySpotId(spotId));
    }

    @GetMapping("/{id}")
    public R<FlowData> getById(@PathVariable Long id) {
        return R.ok(flowService.getFlowDataById(id));
    }

    @PostMapping
    public R<Void> create(@RequestBody FlowData flowData) {
        boolean success = flowService.createFlowData(flowData);
        return success ? R.ok() : R.fail("创建失败");
    }

    @PutMapping
    public R<Void> update(@RequestBody FlowData flowData) {
        boolean success = flowService.updateFlowData(flowData);
        return success ? R.ok() : R.fail("更新失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean success = flowService.deleteFlowData(id);
        return success ? R.ok() : R.fail("删除失败");
    }
}
