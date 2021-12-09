package springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springdemo.dao.CustomerDAO;
import springdemo.entity.Customer;

@Service
public class CustomerServiceImplement implements CustomerService 
{
	// injecting  Customer DAO
	@Autowired
	private CustomerDAO customerDAO;
	
	@Override
	@Transactional
	public List<Customer> getCustomers(int sortValue) 
	{
		return customerDAO.getCustomers(sortValue);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer) 
	{
		customerDAO.saveCustomer(customer);
	}

	@Override
	@Transactional
	public Customer getCustomer(int id)
	{
		
		return customerDAO.getCustomer(id);
	}

	@Override
	@Transactional
	public void deleteCustomer(int id) 
	{
		customerDAO.deleteCustomer(id);
		
		
	}
	
	@Override
    @Transactional
    public List<Customer> searchCustomers(String searchValue) 
	{
        return customerDAO.searchCustomers(searchValue);
    }

}
