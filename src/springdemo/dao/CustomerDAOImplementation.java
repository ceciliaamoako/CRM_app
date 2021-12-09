package springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import springdemo.entity.Customer;
import springdemo.util.SortUtil;

@Repository
public class CustomerDAOImplementation implements CustomerDAO 
{
	// inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	


	@Override
	public List<Customer> getCustomers(int sortValue) 
	{

		// get the current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// determine sort field
		String fieldName = null;

		switch (sortValue) 
		{
			case SortUtil.FIRST_NAME: 
				fieldName = "firstName";
				break;
			case SortUtil.LAST_NAME:
				fieldName = "lastName";
				break;
			case SortUtil.EMAIL:
				fieldName = "email";
				break;
			default:
				// default to sort is by the lastName
				fieldName = "lastName";
		}

		// make your query 
		String query = "from Customer order by " + fieldName;
		Query <Customer> theQuery = session.createQuery(query, Customer.class);

		
		List<Customer> customers = theQuery.getResultList();

		// return the list of customers		
		return customers;
	}


	@Override
	public void saveCustomer(Customer customer) 
	{
		// get current hibernate session
		Session session = sessionFactory.getCurrentSession();

		// either save or update the customer
		session.saveOrUpdate(customer);
	}


	@Override
	public Customer getCustomer(int id) 
	{
		Session session = sessionFactory.getCurrentSession();

		// get customer information using the id
		Customer customer = session.get(Customer.class, id);

		// return the customer info
		return customer;
	}


	@Override
	public void deleteCustomer(int id) 
	{
		Session session = sessionFactory.getCurrentSession();

		// delete from customer database using the id with a  query 
		Query theQuery = session.createQuery("delete from Customer "
				+ "where id=:customerId");

		theQuery.setParameter("customerId", id);

		theQuery.executeUpdate();
	}
	
	@Override
	public List<Customer> searchCustomers(String searchValue) 
	{
		Session session = sessionFactory.getCurrentSession();

		Query theQuery = null;

		if (searchValue != null && searchValue.trim().length() > 0) 
		{
			// search for first name or last name
			theQuery =session.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%" + searchValue.toLowerCase() + "%");
		}
		else 
		{
			// if search value is empty, get a list of all customers
			theQuery = session.createQuery("from Customer", Customer.class);            
		}

		// get result list
		List<Customer> customers = theQuery.getResultList();
     
		return customers;

	}

	

}
