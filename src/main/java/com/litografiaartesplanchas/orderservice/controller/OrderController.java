package com.litografiaartesplanchas.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.litografiaartesplanchas.orderservice.model.Order;
import com.litografiaartesplanchas.orderservice.service.OrderService;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/")
    public ResponseEntity<?> getAllOrders() {
    try {
        List<Order> orders = orderService.getAll(); 
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    } catch(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400,\"message\": \"Something Went Wrong\"}");
    }
}
    @GetMapping("/approved")
    public ResponseEntity<?> getApprovedOrders() {
        try {
            List<Order> approvedOrders = orderService.getApprovedOrders(); 
            if (approvedOrders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(approvedOrders);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400,\"message\": \"Error occurred while fetching approved orders: " + e.getMessage() + "\"}");
        }
    }
    @GetMapping("toapprove")
    public ResponseEntity<?> getNotApprovedOrder() {
        try {
            List<Order> notApprovedOrders = orderService.getNotApprovedOrders();
            if (notApprovedOrders.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(notApprovedOrders);
        } catch(Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400,\"message\": \"Error occurred while fetching toapprove orders: " + e.getMessage() + "\"}");
        }
    }
    


    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody Order order) {
        try {
            orderService.createOrder(order);
            return ResponseEntity.ok().body("{\"status\": 200, \"message\": \"Order created successfully\"}");
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

    @PatchMapping("/{id}/approve")
    public ResponseEntity<?> approveOrder(@PathVariable int id) {
    try {
        orderService.approveOrder(id);
        return ResponseEntity.ok("{\"status\": 200, \"message\": \"ok\"}");
    } catch (Exception e) {
        if (e.getMessage().contains("Order not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"status\": 404, \"message\": \"Order not found\"}");
        } else if (e.getMessage().contains("Order is already approved")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"status\": 409, \"message\": \"Order is already approved\"}");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400, \"message\": \"" + e.getMessage() + "\"}");
    }
}

    @PatchMapping("/{id}/disapprove")
    public ResponseEntity<?> disapproveOrder(@PathVariable int id) {
        try {
            orderService.disapproveOrder(id);
            return ResponseEntity.ok("{\"status\": 200, \"message\": \"ok\"}");
        } catch (Exception e) {
            if (e.getMessage().contains("Order not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"status\": 404, \"message\": \"Order not found\"}");
            } else if (e.getMessage().contains("Order is already disapproved")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"status\": 409, \"message\": \"Order is already disapproved\"}");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"status\": 400, \"message\": \"" + e.getMessage() + "\"}");
        }
}





}
