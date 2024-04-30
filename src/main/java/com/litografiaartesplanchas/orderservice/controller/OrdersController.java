package com.litografiaartesplanchas.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.litografiaartesplanchas.orderservice.model.Orders;
import com.litografiaartesplanchas.orderservice.service.OrdersService;

public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody Orders order) {
        try {
            ordersService.createOrder(order);
            return ResponseEntity.status(HttpStatus.OK).body("Order created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating order: " + e.getMessage());
        }
    }

}
