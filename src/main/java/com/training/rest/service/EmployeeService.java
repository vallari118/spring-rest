package com.training.rest.service;

import com.training.rest.model.Employee;

import java.util.List;

public interface EmployeeService {
    Employee findById(long id);

    Employee findByName(String name);

    void deleteAll();

    void deleteById(long id);

    void saveEmployee(Employee employee);

    void updateEmployee(Employee employee);

    List<Employee> findAll();

    public boolean isEmployeeExist(Employee employee);

}
