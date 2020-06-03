package com.training.rest.service;

import com.training.rest.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    private static final AtomicLong counter = new AtomicLong();
    private static List<Employee> employees;

    static {
        employees = populateDummyEmployees();
    }

    private static List<Employee> populateDummyEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(new Employee(counter.incrementAndGet(), "Vallari", "Devops"));
        employeeList.add(new Employee(counter.incrementAndGet(), "Anjali", "Testing"));
        employeeList.add(new Employee(counter.incrementAndGet(), "Priyanka", "ProdSup"));
        return employeeList;
    }

    @Override
    public Employee findById(long id) {
        for(Employee employee: employees){
            if(employee.getId()==id){
                return employee;
            }
        }

        return null;
    }

    @Override
    public Employee findByName(String name) {
        for(Employee employee:employees){
            if(employee.getName().equalsIgnoreCase(name)){
                return employee;
            }
        }
        return null;
    }

    @Override
    public void deleteAll() {
        employees.clear();

    }

    @Override
    public void deleteById(long id) {
        for(Iterator<Employee> iterator = employees.iterator(); iterator.hasNext();){
            Employee employee = iterator.next();
            if(employee.getId() == id){
                iterator.remove();
            }
        }

    }

    @Override
    public void saveEmployee(Employee employee) {
        employee.setId(counter.incrementAndGet());
        employees.add(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        int index = employees.indexOf(employee);
        employees.set(index,employee);

    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public boolean isEmployeeExist(Employee employee) {
        return findByName(employee.getName())!=null;
    }
}
