package com.litografiaartesplanchas.orderservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.litografiaartesplanchas.orderservice.model.Client;
import com.litografiaartesplanchas.orderservice.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByApproval(Boolean approval);
    List<Order> findByClient(Client client);
}

