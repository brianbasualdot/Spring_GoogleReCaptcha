package com.org.google.service;

import com.org.google.entities.EmployeeEntity;

import java.util.List;

public interface EmployeeService {

    List<EmployeeEntity> findAll();

    EmployeeEntity findById(Long id);

    void createEmployee(EmployeeEntity employeeEntity);

    void deleteEmployeeById(Long id);
}
