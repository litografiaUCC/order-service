package com.litografiaartesplanchas.orderservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litografiaartesplanchas.orderservice.model.Client;
import com.litografiaartesplanchas.orderservice.model.Order;
import com.litografiaartesplanchas.orderservice.model.ServiceModule;
import com.litografiaartesplanchas.orderservice.repository.ClientRepository;
import com.litografiaartesplanchas.orderservice.repository.OrderRepository;
import com.litografiaartesplanchas.orderservice.repository.ServiceRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ServiceRepository serviceRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public List<Order> getApprovedOrders() {
        List<Order> approvedOrders = orderRepository.findByApproval(true);
        if (approvedOrders.isEmpty()) {
            throw new RuntimeException("No approved orders found.");
        }
        return approvedOrders;
    }

    public List<Order> getNotApprovedOrders() {
        List<Order> notApprovedOrders = orderRepository.findByApproval(null);
        if (notApprovedOrders.isEmpty()) {
            throw new RuntimeException("No orders to approve");
        }
        return notApprovedOrders;
    }


    public Order createOrder(Order order) throws Exception {
        try {
            Client client = clientRepository.findById(order.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client not found with ID: " + order.getClient().getId()));
    
            ServiceModule services = serviceRepository.findById(order.getService().getId())
                    .orElseThrow(() -> new RuntimeException("Service not found with ID: " + order.getService().getId()));
    
            order.setClient(client);
            order.setService(services);
    
            return orderRepository.save(order);
        } catch (Exception e) {
            throw new Exception("Error occurred while creating order: " + e.getMessage());
        }
    }

    public void approveOrder(int orderId) throws Exception {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isEmpty()) {
                throw new Exception("Order not found with ID: " + orderId);
            }
            Order order = optionalOrder.get();
            if (Boolean.TRUE.equals(order.isApproval())) {
                throw new Exception("Order is already approved.");
            }
            order.setApproval(true);
            orderRepository.save(order);
        } catch (Exception e) {
            throw new Exception("Error occurred while approving order: " + e.getMessage());
        }
    }

    public void disapproveOrder(int orderId) throws Exception {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isEmpty()) {
                throw new Exception("Order not found with ID: " + orderId);
            }
            Order order = optionalOrder.get();
            if (Boolean.FALSE.equals(order.isApproval())) {
                throw new Exception("Order is already disapproved.");
            }
            order.setApproval(false);
            orderRepository.save(order);
        } catch (Exception e) {
            throw new Exception("Error occurred while disapproving order: " + e.getMessage());
        }
    }
    

}
