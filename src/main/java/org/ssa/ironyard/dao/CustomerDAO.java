package org.ssa.ironyard.dao;

import java.util.List;

import org.ssa.ironyard.model.Customer;

public interface CustomerDAO
{
    Customer insert(Customer customer);
    boolean delete(Customer toDelete);
    Customer update(Customer customer);
    Customer read(int id);
    List<Customer> readAll();
    List<Customer> readFirstName(String firstName);
    List<Customer> readLastName(String lastName);
    boolean deleteAll();
    void close();
}
