package com.litografiaartesplanchas.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.litografiaartesplanchas.orderservice.model.Services;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Integer>{
	
}