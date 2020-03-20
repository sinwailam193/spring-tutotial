package com.springweb.crud.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.springweb.crud.entity.Employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("JPAImpl")
public class EmployeeDAOJPAImpl implements EmployeeDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Employee> getAllEmployee() {
        Query theQuery = entityManager.createQuery("from Employee");

        @SuppressWarnings("unchecked")
        List<Employee> employees = theQuery.getResultList();

        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = entityManager.find(Employee.class, id);
        return employee;
    }

    @Override
    public void addEmployee(Employee employee) {
        Employee dbEmployee = entityManager.merge(employee);
        employee.setId((dbEmployee.getId()));
    }

    @Override
    public void deleteEmployeeById(int id) {
        Query query = entityManager.createQuery("delete from Employee where id=:employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }

}