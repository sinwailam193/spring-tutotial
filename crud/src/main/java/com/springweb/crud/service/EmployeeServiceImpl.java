package com.springweb.crud.service;

import java.util.List;
import java.util.Optional;

import com.springweb.crud.dao.EmployeeRepository;
import com.springweb.crud.entity.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/*
    No need for @Transactional since JpaRepository already does it
    By using JpaRepository, findAll, findById, save, deleteById comes with it
*/

@Service
@Repository("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(int id) {
        Optional<Employee> res = employeeRepository.findById(id);
        if (res.isPresent()) {
            return res.get();
        }
        return null;
    }

    @Override
    public void addEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        employeeRepository.deleteById(id);
    }

}