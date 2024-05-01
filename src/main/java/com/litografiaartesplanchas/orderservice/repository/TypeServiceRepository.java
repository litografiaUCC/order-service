package com.litografiaartesplanchas.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.litografiaartesplanchas.orderservice.model.TypeService;

@Repository
public interface TypeServiceRepository extends JpaRepository<TypeService, Integer>{

}