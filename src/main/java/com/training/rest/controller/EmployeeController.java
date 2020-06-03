package com.training.rest.controller;

import com.training.rest.model.Employee;
import com.training.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping(value = "/employees/")
    public ResponseEntity<List<Employee>> listAllUsers(){
        List<Employee> employee = employeeService.findAll();
        if(employee.isEmpty()){
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<List<Employee>>(employee,HttpStatus.OK);
        }
    }

    @GetMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") long id){
        System.out.println("Fetching user with id: "+id);
        Employee employee = employeeService.findById(id);
        if(employee == null){
            System.out.println("User with id "+id+" not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<Employee>(employee,HttpStatus.OK);
        }

    }

    @PostMapping(value = "/employees")
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder){
        System.out.println("Creating Employee "+ employee.getName());
        if(employeeService.isEmployeeExist(employee)){
            System.out.println("An Employee with name "+employee.getName()+" already exists");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }
        employeeService.saveEmployee(employee);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/employees/{id}").buildAndExpand(employee.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee){
        System.out.println("Updating user with id: "+id);
        Employee currentEmployee = employeeService.findById(id);
        if(currentEmployee == null){
            System.out.println("Employee with id "+id+" not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        currentEmployee.setName(employee.getName());
        currentEmployee.setDept(employee.getDept());
        employeeService.updateEmployee(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }

    @DeleteMapping(value = "/employees/{id}")
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") long id){
        System.out.println("Fetching and deleting Employee with id: "+id);

        Employee employee = employeeService.findById(id);
        if(employee == null){
            System.out.println("Employee with id "+id+" not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        employeeService.deleteById(id);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }


}
