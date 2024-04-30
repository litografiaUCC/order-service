package com.litografiaartesplanchas.orderservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.litografiaartesplanchas.orderservice.model.Client;
import com.litografiaartesplanchas.orderservice.model.Orders;
import com.litografiaartesplanchas.orderservice.model.Services;
import com.litografiaartesplanchas.orderservice.repository.ClientRepository;
import com.litografiaartesplanchas.orderservice.repository.OrdersRepository;
import com.litografiaartesplanchas.orderservice.repository.ServicesRepository;

@Service
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ServicesRepository serviceRepository;

    public List<Orders> getAll() {
        return ordersRepository.findAll();
    }

    public Orders createOrder(Orders order) {
        Client client = clientRepository.findById(order.getClient().getId())
                .orElseThrow(() -> new RuntimeException("Client not found with ID: " + order.getClient().getId()));

        Services services = serviceRepository.findById(order.getService().getId())
                .orElseThrow(() -> new RuntimeException("Service not found with ID: " + order.getService().getId()));

        order.setClient(client);
        order.setService(services);

        return ordersRepository.save(order);
    }

}
