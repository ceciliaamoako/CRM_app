package springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springdemo.dao.CustomerDAO;
import springdemo.entity.Customer;
import springdemo.service.CustomerService;
import springdemo.util.SortUtil;

@Controller
@RequestMapping("/customer")
public class CustomerController 
{
	// injecting CustomerService
	@Autowired
	private CustomerService customerService;
	
	// handler method to list customers and sort the customer information
	@GetMapping("/list")
	public String listCustomers(Model model, @RequestParam(required=false) String sort)
	{

		List<Customer> customers = null;

		// check for sort field
		if (sort != null) 
		{
			int sortValue = Integer.parseInt(sort);
			customers = customerService.getCustomers(sortValue);			
		}
		else 
		{
			// default sorting field is last name
			customers = customerService.getCustomers(SortUtil.FIRST_NAME);
		}

		// add the customers
		model.addAttribute("customers", customers);

		return "list-customers";

	}
	
	// handler method to add the customer
	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model model)
	{
		Customer customer = new Customer();

		model.addAttribute("customer", customer);

		return "customer-form";
	}

	//handler method to save the customer to database
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer customer)
	{	
		customerService.saveCustomer(customer);

		return "redirect:/customer/list";
	}

	// handler method to update the customer information
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int id, 
			Model model)
	{
		// retrieve customer by id
		Customer customer = customerService.getCustomer(id);
 
		model.addAttribute("customer", customer);

		return "customer-form";
	}

	// handler method to delete the customer
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int id)
	{
		customerService.deleteCustomer(id);

		return "redirect:/customer/list";
	}

	// handler method to search for information
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("searchValue") String searchValue,
			Model model) 
	{
		// search customers from the service
		List<Customer> customers = customerService.searchCustomers(searchValue);

		// add the customers to the model
		model.addAttribute("customers", customers);
		return "list-customers";        
	}

}
