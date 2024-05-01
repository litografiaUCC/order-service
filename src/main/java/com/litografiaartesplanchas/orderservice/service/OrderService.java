package com.litografiaartesplanchas.orderservice.service;

import java.util.List;

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
    

}
