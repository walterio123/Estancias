package Login.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Login.web.Entity.Customer;


public interface CustomerRepository extends JpaRepository<Customer, String>{

	@Query("Select a From Customer a WHERE a.id= :id")
	public Customer findCustomerForId(String id);
	
	
}
