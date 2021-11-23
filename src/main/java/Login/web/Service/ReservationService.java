package Login.web.Service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration;
import org.springframework.stereotype.Service;

import Login.web.Entity.Customer;
import Login.web.Entity.House;
import Login.web.Entity.Reservation;
import Login.web.Repository.CustomerRepository;
import Login.web.Repository.HouseRepository;
import Login.web.Repository.ReservationRepository;

@Service
public class ReservationService {
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private HouseRepository houseRepository;
	
	@Transactional
	public void registerReservation(String guest,Date dateFrom, Date dateTo, String idCustomer, String idHouse ) throws Exception {
		validation(guest, dateFrom, dateTo);
		//Searching for the client by id
		Optional<Customer>resOptionalCustomer=customerRepository.findById(idCustomer);
		if(resOptionalCustomer.isPresent()) {
		//If the client exists, I look for the house.
			Customer customer=resOptionalCustomer.get();
			Optional<House>resOptionalHouse=houseRepository.findById(idHouse);
			if(resOptionalHouse.isPresent()) {
			House house=resOptionalHouse.get();
		//If the client exists and the house exists and the data is validated, I create the reservation
				Reservation reservation=new Reservation();
				reservation.setGuest(guest);
				reservation.setDateFrom(dateFrom);
				reservation.setDateTo(dateTo);
				reservation.setCustomer(customer);
				reservation.setHouse(house);
				reservation.setAlta(true);
				
				reservationRepository.save(reservation);
			}else {
				throw new Exception("the house you are trying to register on the loan does not exist");
			}
		}else {
			throw new Exception("the client you are trying to register in the reservation does not exist");
		}
	}
	@Transactional
	public void modifyReservation(String id,String guest,Date dateFrom,Date dateTo) throws Exception {
		validation(guest, dateFrom, dateTo);
		//looking if the reservation exists in the database
		Optional<Reservation>respOptional=reservationRepository.findById(id);
		if(respOptional.isPresent()) {
			Reservation reservation=respOptional.get();
			reservation.setGuest(guest);
			reservation.setDateFrom(dateFrom);
			reservation.setDateTo(dateTo);
			
			reservationRepository.save(reservation);
		}else {
			throw new Exception("the reservation you are looking for does not exist in the database");
		}
	}
	
	
	@Transactional
	public void deleteReservation(String id) throws Exception {
		//find the reservation
		Optional<Reservation>resOptional=reservationRepository.findById(id);
		if(resOptional.isPresent()) {
			//if the reservation exists, cancel it.
			Reservation reservation=resOptional.get();
			reservation.setAlta(false);
			reservationRepository.save(reservation);
		}else {
			//if the reservation does not exist.
			throw new Exception("The reservation you are looking for does not exist in the database.");
		}
	}
	
	public void validation(String guest,Date dateFrom, Date dateTo) throws Exception {
		
		if (guest.isEmpty() || guest == null) {
			throw new Exception("Guest is Empty");
		}
		if (dateFrom == null) {
			throw new Exception("The date of entry is Empty");
		}
		if (dateTo == null) {
			throw new Exception("The date of departure is Empty");
		}
	
		
	}
}
