package com.litografiaartesplanchas.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.litografiaartesplanchas.orderservice.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
