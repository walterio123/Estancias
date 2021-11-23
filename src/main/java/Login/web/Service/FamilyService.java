package Login.web.Service;

import java.util.Optional;

import javax.transaction.Transactional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Login.web.Entity.Family;
import Login.web.Entity.House;
import Login.web.Entity.User;
import Login.web.Repository.FamilyRepository;
import Login.web.Repository.HouseRepository;
import Login.web.Repository.UserRepository;


@Service
public class FamilyService {
	@Autowired
	private FamilyRepository familyRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private HouseRepository houseRepository;
	
	@Transactional
	private void registerFamily( String name,Integer minAge,Integer maxAge, Integer numSons, String email, String idUser,String idHouse) throws Exception {
		validation(name, minAge, maxAge, numSons, email);
		//find User
		Optional<User>resOptionalUser=userRepository.findById(idUser);
		if (resOptionalUser.isPresent()) {
			//creation of the user to set in the family
			User user=resOptionalUser.get();
			//find the house
			Optional<House>resOptionalHouse=houseRepository.findById(idHouse);
			House house=resOptionalHouse.get();
			
			//creation of the family
			Family family=new Family();
			family.setName(name);
			family.setMinAge(minAge);
			family.setMaxAge(maxAge);
			family.setNumSons(numSons);
			family.setEmail(email);
			family.setAlta(true);
			family.setHouse(house);
			family.setUser(user);
			familyRepository.save(family);
		}else {
			throw new Exception("User is missing");
		}
	}
	
	@Transactional
	private void modifyFamily(String id, String name,Integer minAge,Integer maxAge, Integer numSons, String email, String idUser,String idHouse) throws Exception {
		validation(name, minAge, maxAge, numSons, email);
		//find Family
		Optional<Family>respOptional=familyRepository.findById(id);
		if (respOptional.isPresent()) {
			//find User
			Optional<User>resOptionalUser=userRepository.findById(idUser);
			if (resOptionalUser.isPresent()) {
				//creation of the user to set in the family
				User user=resOptionalUser.get();
				//find the house
				Optional<House>resOptionalHouse=houseRepository.findById(idHouse);
				House house=resOptionalHouse.get();
				//creation of the family
				Family family=respOptional.get();
				family.setName(name);
				family.setMinAge(minAge);
				family.setMaxAge(maxAge);
				family.setNumSons(numSons);
				family.setEmail(email);
				family.setHouse(house);
				family.setAlta(true);
				family.setUser(user);
				familyRepository.save(family);
			}else {
				throw new Exception("User is missing");
			}
		}else {
			throw new Exception("The family you are trying to modify does not exist.");
		}
		
	}
	
	public void delete(String id) throws Exception {
		
		Optional<Family>respOptional=familyRepository.findById(id);
		if (respOptional.isPresent()) {
			
			//looking for the family house to see if it has any active
			
			Family family=respOptional.get();
			
			House house=family.getHouse();
			//If the house is active, the family cannot be canceled
			if(house.isAlta()==true) {
				throw new Exception( "The family has an active house cannot be canceled .");
			}else {
				//If the family does not have an active house, it is canceled
				family.setAlta(false);
				familyRepository.save(family);
			}
		}else {
			throw new Exception("The family you are trying to modify does not exist.");
		}
		
	}
	
	
	
	private void validation( String name,Integer minAge,Integer maxAge, Integer numSons, String email) throws Exception {
		
		if (name == null || name.isEmpty()) {
			throw new Exception("The family does not have name.");
		}
		if (minAge == null ) {
			throw new Exception("The minimum age for the family is missing.");
		}
		if (maxAge == null ) {
			throw new Exception("The maximum age for the family is missing.");
		}
		if (numSons == null ) {
			throw new Exception("The num of sons is missing.");
		}
		
		if (email == null || name.isEmpty()) {
			throw new Exception("The family does not have name.");
		
		}
	
	}
}
