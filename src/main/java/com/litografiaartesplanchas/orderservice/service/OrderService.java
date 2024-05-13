package com.litografiaartesplanchas.orderservice.service;

import java.sql.Timestamp;
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

    public Optional<Order> getOrderById(Integer orderId) throws Exception {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            return optionalOrder;
        } catch (Exception e) {
            throw new Exception("Error occurred while finding order: " + orderId + ": "+ e.getMessage());
        }
    }

    public List<Order> getApprovedOrders() {
        List<Order> approvedOrders = orderRepository.findByApproval(true);
        return approvedOrders;
    }

    public List<Order> getNotApprovedOrders() {
        List<Order> notApprovedOrders = orderRepository.findByApproval(null);
        return notApprovedOrders;
    }


    public Order createOrder(Order order) throws Exception {
        try {
            Client client = clientRepository.findById(order.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client not found with ID: " + order.getClient().getId()));
    
            ServiceModule services = serviceRepository.findById(order.getService().getId())
                    .orElseThrow(() -> new RuntimeException("Service not found with ID: " + order.getService().getId()));
    
            order.setDate(new Timestamp(System.currentTimeMillis()));
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

    public List<Order> getOrdersByClientId(int clientId) throws Exception {
        try {
            Optional<Client> optionalClient = clientRepository.findById(clientId);
            if (optionalClient.isEmpty()) {
                throw new Exception("Client not found");
            }
            Client client = optionalClient.get();
            List<Order> orders = orderRepository.findByClient(client);
            if (orders.isEmpty()) {
                throw new Exception("Client has no orders");
            }
            return orders;
        } catch (Exception e) {
            throw new Exception("Error occurred while finding client: " + e.getMessage());
        }
    }

    public void updateStatus(int orderId, int status) throws Exception {
        try {
            Optional<Order> optionalOrder = orderRepository.findById(orderId);
            if (optionalOrder.isEmpty()) {
                throw new Exception("Order not found with ID");
            }
            Order order = optionalOrder.get();
            if (!Boolean.TRUE.equals(order.isApproval())) {
                throw new Exception("Order is not approved");
            }
            order.setStatus(status);
            orderRepository.save(order);
        } catch (Exception e) {
            throw new Exception("Error occurred while updating order status: " + e.getMessage());
        }
    }
}
