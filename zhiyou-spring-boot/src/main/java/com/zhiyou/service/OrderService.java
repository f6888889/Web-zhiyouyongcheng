package com.zhiyou.service;

import com.zhiyou.model.Order;
import com.zhiyou.repository.JsonDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {

    private final JsonDataRepository repository;

    public OrderService(JsonDataRepository repository) {
        this.repository = repository;
    }

    private static final String FILE_NAME = "order.json";

    public List<Order> getAllOrders() {
        return repository.selectList(FILE_NAME, Order.class);
    }

    public Order getOrderById(Long id) {
        return repository.selectById(FILE_NAME, id, Order.class);
    }

    public Order getOrderByOrderNo(String orderNo) {
        List<Order> orders = repository.selectListByField(FILE_NAME, Order.class, "orderNo", orderNo);
        return orders.isEmpty() ? null : orders.get(0);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return repository.selectListByField(FILE_NAME, Order.class, "userId", userId);
    }

    public Order createOrder(Order order) {
        order.setOrderNo("ZY" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 4).toUpperCase());
        order.setStatus("pending");
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        boolean success = repository.insert(FILE_NAME, order);
        return success ? order : null;
    }

    public boolean payOrder(Long id) {
        Order order = repository.selectById(FILE_NAME, id, Order.class);
        if (order == null) {
            return false;
        }
        order.setStatus("paid");
        order.setPayTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return repository.update(FILE_NAME, order);
    }

    public boolean cancelOrder(Long id) {
        Order order = repository.selectById(FILE_NAME, id, Order.class);
        if (order == null) {
            return false;
        }
        order.setStatus("cancelled");
        order.setUpdateTime(LocalDateTime.now());
        return repository.update(FILE_NAME, order);
    }

    public boolean deleteOrder(Long id) {
        return repository.delete(FILE_NAME, id, Order.class);
    }
}
