package com.zhiyou.controller;

import com.zhiyou.common.R;
import com.zhiyou.model.Order;
import com.zhiyou.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    public R<List<Order>> list() {
        return R.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public R<Order> getById(@PathVariable Long id) {
        return R.ok(orderService.getOrderById(id));
    }

    @GetMapping("/no/{orderNo}")
    public R<Order> getByOrderNo(@PathVariable String orderNo) {
        return R.ok(orderService.getOrderByOrderNo(orderNo));
    }

    @GetMapping("/user/{userId}")
    public R<List<Order>> getByUserId(@PathVariable Long userId) {
        return R.ok(orderService.getOrdersByUserId(userId));
    }

    @PostMapping
    public R<Order> create(@RequestBody Order order) {
        Order created = orderService.createOrder(order);
        if (created == null) {
            return R.fail("创建订单失败");
        }
        return R.ok(created);
    }

    @PutMapping("/pay/{id}")
    public R<Void> pay(@PathVariable Long id) {
        boolean success = orderService.payOrder(id);
        return success ? R.ok() : R.fail("支付失败");
    }

    @PutMapping("/cancel/{id}")
    public R<Void> cancel(@PathVariable Long id) {
        boolean success = orderService.cancelOrder(id);
        return success ? R.ok() : R.fail("取消失败");
    }

    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        boolean success = orderService.deleteOrder(id);
        return success ? R.ok() : R.fail("删除失败");
    }
}
