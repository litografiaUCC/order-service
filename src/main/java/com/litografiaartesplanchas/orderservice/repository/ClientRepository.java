package com.litografiaartesplanchas.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.litografiaartesplanchas.orderservice.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
	
}
