package Login.web.Service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Login.web.Entity.Customer;
import Login.web.Entity.House;
import Login.web.Entity.Rent;
import Login.web.Repository.HouseRepository;
import Login.web.Repository.RentRepository;

@Service
public class RentService {
	
	@Autowired
	private HouseRepository houseRepository;
	@Autowired
	private RentRepository rentRepository;
	@Autowired
	private HouseService houseService;
	@Autowired
	private CustomerService customerService;
	
	public String rent(String idCustomer,String idHouse,Date dateFrom,Date dateTo) throws Exception {
		
		//validar datos
		validation(idCustomer, idHouse, dateFrom, dateTo);
		//comprobar que la casa esta disponible
		House house=houseService.findForId(idHouse);
		if(house != null) {
			//creando el cliente
			Customer customer=customerService.findCustomerForId(idCustomer);
			//comprobando que el cliente exista
			if(customer != null) {
				//generar la renta
				Rent rent=new Rent();
				rent.setDateFrom(dateFrom);
				rent.setDateTo(dateTo);
				rent.setAlta(true);
				rent.setCustomer(customer);
				rent.setHouse(house);
				//guardar el alquiler
				rentRepository.save(rent);
				//dar de baja la casa
				house.setAlta(false);
				houseRepository.save(house);
				
			}else {
				throw new Exception("The client does not exist");
			}
		}else {
			throw new Exception("The house is not available");
		}
		
		return "rent";	
	}
	
	public void modifyRent(String id, Date dateFrom,Date dateTo) throws Exception {
		Optional<Rent>resp=rentRepository.findById(id);
		if(resp.isPresent()) {
			Rent rent=resp.get();
			rent.setAlta(true);
			rent.setDateFrom(dateFrom);
			rent.setDateTo(dateTo);
			
			rentRepository.save(rent);
		}else {
			throw new Exception("The rental could not be modified");
		}
	}
	
	public void deleteDown(String id) throws Exception {
		Optional<Rent>resp=rentRepository.findById(id);
		if(resp.isPresent()) {
			Rent rent=resp.get();
			//modificar casa Prestada dar de alta
			House house=houseService.findForId(rent.getHouse().getId());
			house.setAlta(true);
			houseRepository.save(house);
			//
			rent.setAlta(false);
			rentRepository.save(rent);
		}
	}
	
	
	public Rent searchRentForId(String id) throws Exception {
		Optional<Rent>resp=rentRepository.findById(id);
		if(resp.isPresent()) {
			return resp.get(); 
		}else {
			throw new Exception("The rental you are looking for by that id does not exist.");
		}
	}
	public void validation(String idCustomer,String idHouse ,Date dateFrom ,Date dateTo) throws Exception {
		
		if(idHouse == null || idHouse.isEmpty()) {
			throw new Exception("The house is missing");
		}
		if(idCustomer == null || idCustomer.isEmpty()) {
			throw new Exception("The customer is missing");
		}
		if(dateTo == null) {
			throw new Exception("Return date is missing");
		}
		if(dateFrom == null) {
			throw new Exception("Loan date is missing");
		}
		if(dateTo.before(dateFrom)) {
			throw new Exception("The return date is not correctly entered");
		}
		
	}
}
