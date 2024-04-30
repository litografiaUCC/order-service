package com.litografiaartesplanchas.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litografiaartesplanchas.orderservice.model.Orders;
import com.litografiaartesplanchas.orderservice.service.OrdersService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {

    @Autowired
    private OrdersService ordersService;

    @GetMapping("/")
    public ResponseEntity<?> getAllOrders() {
    try {
        List<Orders> orders = ordersService.getAll(); 
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    } catch(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400,\"message\": \"Something Went Wrong\"}");
    }
}

@PostMapping("/create")
public ResponseEntity<?> createOrder(@RequestBody Orders order) {
    try {
        ordersService.createOrder(order);
        return ResponseEntity.ok().body("{\"status\": 200, \"message\": \"Order created successfully\"}");
    } catch (Exception e) {
        e.printStackTrace(); 
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400, \"message\": \"" + e.getMessage() + "\"}");
    }
}


}
