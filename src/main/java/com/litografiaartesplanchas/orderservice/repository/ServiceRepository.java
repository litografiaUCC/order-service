package com.litografiaartesplanchas.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.litografiaartesplanchas.orderservice.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long>{
	
}