package Login.web.Service;


import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Login.web.Entity.Customer;

import Login.web.Entity.UserEst;
import Login.web.Repository.CustomerRepository;

import Login.web.Repository.UserEstRepository;

@Service
public class CustomerService {
	
	@Autowired
	private UserEstRepository userRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	
	public void register(String name, String street,Integer number,String postalCode,String city,String country,String email,String idUser) throws Exception {
		
		Optional<UserEst>respOptional=userRepository.findById(idUser);
		if (respOptional.isPresent()) {
			UserEst user=respOptional.get();
			Customer customer=new Customer();
			validation(name, street, number, postalCode, city, country, email);
			customer.setName(name);
			customer.setStreet(street);
			customer.setNumber(number);
			customer.setPostalCode(postalCode);
			customer.setCity(city);
			customer.setCountry(country);
			customer.setEmail(email);
			customer.setAlta(true);
			customer.setUser(user);
			
			customerRepository.save(customer);
			
			
		}else {
			throw new Exception("User is not present.");
		}
	}
	public void modify(String id,String name, String street,Integer number,String postalCode,String city,String country,String email,String idUser) throws Exception {
		
	Optional<Customer>resOptional=customerRepository.findById(idUser);
	if(resOptional.isPresent()) {
		Customer customer=resOptional.get();
		validation(name, street, number, postalCode, city, country, email);
		Optional<UserEst>respOptionalUser=userRepository.findById(idUser);
		if (respOptionalUser.isPresent()) {
			UserEst user=respOptionalUser.get();
			customer.setName(name);
			customer.setStreet(street);
			customer.setNumber(number);
			customer.setPostalCode(postalCode);
			customer.setCity(city);
			customer.setCountry(country);
			customer.setEmail(email);
			customer.setAlta(true);
			customer.setUser(user);
			
			customerRepository.save(customer);
		}else {
			throw new Exception();
		}
		
	}else {
		throw new Exception();
	}
		
	}
	
	public void removeCustomer(String id) throws Exception {
		Optional<Customer>respOptional=customerRepository.findById(id);
		if (respOptional.isPresent()) {
			//mirar si el cliente tiene alguna reservacion vigente
			//
			Customer customer=respOptional.get();
			customer.setAlta(true);
			customerRepository.save(customer);
		}else {
			throw new Exception("The client trying to unsubscribe does not exist.");
		}
	}
	
	
	
	public Customer findCustomerForId(String id) throws Exception {
		
		Optional<Customer>respOptional=customerRepository.findById(id);
		if(respOptional.isPresent()) {
			Customer customer=respOptional.get();
			return customer;
		}else {
			throw new Exception("There are no users with that id.");
		}
	}
	
	public void validation(String name, String street,Integer number,String postalCode,String city,String country,String email) throws Exception {
		
		if (name.isEmpty()) {
			throw new Exception("Name is Empty");
		}
		if (street.isEmpty()) {
			throw new Exception("Street is Empty");
		}
		if (number == null) {
			throw new Exception("Number of Street is Empty");
		}
		if (postalCode.isEmpty()) {
			throw new Exception("Postal code is Empty");
		}
		if (city.isEmpty()) {
			throw new Exception("City is Empty");
		}
		if (country.isEmpty()) {
			throw new Exception("Country is Empty");
		}
		if (email.isEmpty()) {
			throw new Exception("Email is Empty");
		}
		
	}
}
