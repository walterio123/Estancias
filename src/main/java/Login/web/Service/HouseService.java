package Login.web.Service;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Login.web.Entity.Comment;
import Login.web.Entity.House;
import Login.web.Repository.CommentRepository;
import Login.web.Repository.HouseRepository;

@Service
public class HouseService {
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private HouseRepository houseRepository;
	
	@Transactional
	public void registerHouse( String street, Integer number, String postalCode , String city, String country ,Date dateFrom , Date dateTo ,Integer minDays , Integer maxDays , Double priceDouble , String tipePlace) throws Exception {
	  validation(street, number, postalCode, city, country, dateFrom, dateTo, minDays, maxDays, priceDouble, tipePlace);
		
	  House house =new House();
	  house.setStreet(street);
	  house.setNumber(number);
	  house.setPostalCode(postalCode);
	  house.setCity(city);
	  house.setCountry(country);
	  house.setDateFrom(dateFrom);
	  house.setDateTo(dateTo);
	  house.setMinDays(minDays);
	  house.setMaxDays(maxDays);
	  house.setPrice(priceDouble);
	  house.setTipePlace(tipePlace);
	  house.setAlta(true);
	  house.setComment(null);
	  
	  houseRepository.save(house);
	}
	
	public void modifyHouse(String idHouse, String street, Integer number, String postalCode , String city, String country ,Date dateFrom , Date dateTo ,Integer minDays , Integer maxDays , Double priceDouble , String tipePlace , String idComment ) throws Exception {
		validation(street, number, postalCode, city, country, dateFrom, dateTo, minDays, maxDays, priceDouble, tipePlace);
		//looking for the house that you want to modify
		Optional<House>respOptional=houseRepository.findById(idHouse);
		if(respOptional.isPresent()) {
			House house=respOptional.get();
			//find comment
			Optional<Comment>resOptionalComment=commentRepository.findById(idComment);
			if (resOptionalComment.isPresent()) {
				Comment comment=resOptionalComment.get();
				  house.setStreet(street);
				  house.setNumber(number);
				  house.setPostalCode(postalCode);
				  house.setCity(city);
				  house.setCountry(country);
				  house.setDateFrom(dateFrom);
				  house.setDateTo(dateTo);
				  house.setMinDays(minDays);
				  house.setMaxDays(maxDays);
				  house.setPrice(priceDouble);
				  house.setTipePlace(tipePlace);
				  house.setAlta(true);
				  house.setComment(comment);
				  houseRepository.save(house);
			}else {
				throw new Exception("The house comment is missing.");
			}
			
		}else {
			throw new Exception("The id does not correspond to some house.");
		}
	}
	//Delete house Implements boolean?
	public void delete(String id) throws Exception {
		
		//find House
		Optional<House>respOptional=houseRepository.findById(id);
		if(respOptional.isPresent()) {
			House house=respOptional.get();
			house.setAlta(false);
			houseRepository.save(house);
		}else {
			throw new Exception("The house cannot be removed.");
		}
	}
	
	
	public void validation(String street, Integer number, String postalCode , String city, String country ,Date dateFrom , Date dateTo ,Integer minDays, Integer maxDays , Double priceDouble , String tipePlace ) throws Exception {
		if (street == null || street.isEmpty()) {
			throw new Exception("The house does not have address.");
		}
		if (number == null ) {
			throw new Exception( "The house does not have an address number.");
		}
		
		if (postalCode == null || postalCode.isEmpty()) {
			throw new Exception("The house does not have a Zip Code.");
		}
		if (city == null || city.isEmpty()) {
			throw new Exception("The house does not have City.");
		}
		if (country == null || country.isEmpty()) {
			throw new Exception("The house does not have Country.");
		}
		if (dateFrom == null ) {
			throw new Exception("The date of entry is missing.");
		}
		if (dateTo == null ) {
			throw new Exception("Departure date is missing.");
		}
		if(dateFrom.before (dateTo)) {
			throw new Exception("The rental start date is after the start date.");
		}
		
		if (minDays == null ) {
			throw new Exception("The minimum number of days is missing.");
		}
		if (maxDays == null ) {
			throw new Exception("The maximun number of days is missing.");
		}
		if (priceDouble == null  ) {
			throw new Exception("The rental price is missing.");
		}
		if (tipePlace == null || tipePlace.isEmpty()) {
			throw new Exception("The type of rental housing is missing");
		}
		
	}
}
