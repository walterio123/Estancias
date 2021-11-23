package Login.web.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import Login.web.Entity.User;
import Login.web.Repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	
	public void register( String alias,String email,String password) throws Exception{
		
		validation(alias, email, password);
		User user=new User();
		
		user.setAlias(alias);
		user.setEmail(email);
		user.setPassword(password);
		user.setDischarge(new Date());
		user.setUnsubscribe(null);
		
		userRepository.save(user);
		
	}

	private void modify(String id,String alias,String email,String password) throws Exception {
		Optional<User>respOptional=userRepository.findById(id);
		if(respOptional.isPresent()) {
			validation(alias, email, password);
			User user=respOptional.get();
			user.setAlias(alias);
			user.setEmail(email);
			user.setPassword(password);
			
			userRepository.save(user);
		}else {
			throw new Exception("Username does not exist.");
		}
	}
	
	private void remove(String id) throws Exception {
		Optional<User>respOptional=userRepository.findById(id);
		if (respOptional.isPresent()) {
			User user=respOptional.get();
			user.setUnsubscribe(new Date());
			userRepository.save(user);
		}else {
			throw new Exception("Username does not exist.");
		}
	}
	
	public User findUserForId(String id) throws Exception {
		
		Optional<User>respuestaOptional=userRepository.findById(id);
		if(respuestaOptional.isPresent()) {
			return respuestaOptional.get();
		}else {
			
			throw new Exception("User does not exist.");
		}
		
	}
	
	public List<User> findAllUserForAlias(String alias) throws Exception {
		
		List<User> user=userRepository.findByAlias(alias);
		if(user != null) {
			return user;
		}else {
			
			throw new Exception("There are no users with that alias.");
		}
		
	}
	
	
	public void validation(String alias,String email,String password) throws Exception {
		if (alias.isEmpty()) {
			throw new Exception("Name is Empty");
		}
		if (email.isEmpty()) {
			throw new Exception("Email is Empty");
		}
		if (password.isEmpty()) {
			throw new Exception("Password is Empty");
		}
		
	}
}
