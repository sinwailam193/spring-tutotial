package com.springweb.crud.service;

import java.util.List;

import com.springweb.crud.entity.Employee;

public interface EmployeeService {
    public List<Employee> getAllEmployee();

    public Employee getEmployeeById(int id);

    public void addEmployee(Employee employee);

    public void deleteEmployeeById(int id);
}