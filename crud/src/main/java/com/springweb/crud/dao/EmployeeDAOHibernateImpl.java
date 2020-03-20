package com.springweb.crud.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.springweb.crud.entity.Employee;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("hibernateImpl")
public class EmployeeDAOHibernateImpl implements EmployeeDAO {
    @Autowired
    private EntityManager entityManger;

    // @Autowired
    // public EmployeeDAOHibernateImpl(EntityManager entityManager) {
    // this.entityManger = entityManager;
    // }

    @Override
    public List<Employee> getAllEmployee() {
        // get the current hibernate session
        Session currentSession = entityManger.unwrap(Session.class);

        // create a query
        Query<Employee> theQuery = currentSession.createQuery("from Employee", Employee.class);

        // execute query and get result list
        List<Employee> employees = theQuery.getResultList();

        // return the results
        return employees;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Session currentSession = entityManger.unwrap(Session.class);
        Employee employee = currentSession.get(Employee.class, id);
        return employee;
    }

    @Override
    public void addEmployee(Employee employee) {
        Session currentSession = entityManger.unwrap(Session.class);
        currentSession.saveOrUpdate(employee);
    }

    @Override
    public void deleteEmployeeById(int id) {
        Session currentSession = entityManger.unwrap(Session.class);
        Query<?> theQuery = currentSession.createQuery("delete from Employee where id=:employeeId");
        theQuery.setParameter("employeeId", id);
        theQuery.executeUpdate();
    }
}