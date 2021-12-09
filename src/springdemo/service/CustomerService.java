package springdemo.service;

import java.util.List;

import springdemo.entity.Customer;

public interface CustomerService 
{
	Customer getCustomers = null;

	public List<Customer> getCustomers(int sortValue);
	
	public void saveCustomer(Customer customer);

	public Customer getCustomer(int id);

	public void deleteCustomer(int id);
	
	public List<Customer> searchCustomers(String searchValue);
}
