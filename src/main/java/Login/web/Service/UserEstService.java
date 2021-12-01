package Login.web.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import Login.web.Entity.UserEst;
import Login.web.Enums.Rol;
import Login.web.Repository.UserEstRepository;


@Service
public class UserEstService implements UserDetailsService{
	//public class UserEstService {
	
	@Autowired
	private UserEstRepository userEstRepository;
	
	@Transactional
	public void register( @RequestParam String alias,@RequestParam String email,@RequestParam String password) throws Exception{
		
		validation(alias, email, password);
		UserEst userEst=new UserEst();
		
		userEst.setAlias(alias);
		userEst.setEmail(email);
		//encriptando clave
		String encripted= new BCryptPasswordEncoder().encode(password);
		userEst.setPassword(encripted);
	
		userEst.setDischarge(new Date());
		userEst.setUnsubscribe(null);
		userEst.setRol(Rol.USUARIO);
		
		userEstRepository.save(userEst);
		
	}
	@Transactional
	public void modify(String id,String alias,String email,String password) throws Exception {
		Optional<UserEst>respOptional=userEstRepository.findById(id);
		if(respOptional.isPresent()) {
			validation(alias, email, password);
			UserEst userEst=respOptional.get();
			userEst.setAlias(alias);
			userEst.setEmail(email);
			//encriptando clave
			String encripted= new BCryptPasswordEncoder().encode(password);
			userEst.setPassword(encripted);
			
			userEstRepository.save(userEst);
		}else {
			throw new Exception("Username does not exist.");
		}
	}
	@Transactional
	private void remove(String id) throws Exception {
		Optional<UserEst>respOptional=userEstRepository.findById(id);
		if (respOptional.isPresent()) {
			UserEst userEst=respOptional.get();
			userEst.setUnsubscribe(new Date());
			userEstRepository.save(userEst);
		}else {
			throw new Exception("Username does not exist.");
		}
	}
	
	public UserEst findUserEstForId(String id) throws Exception {
		
		Optional<UserEst>respuestaOptional=userEstRepository.findById(id);
		if(respuestaOptional.isPresent()) {
			return respuestaOptional.get();
		}else {
			
			throw new Exception("User does not exist.");
		}
		
	}
	
	public List<UserEst> findAllUserForAlias(String alias) throws Exception {
		
		List<UserEst> user=userEstRepository.findByAlias(alias);
		if(user != null) {
			return user;
		}else {
			
			throw new Exception("There are no users with that alias.");
		}
		
	}
	public UserEst findUserForId(String alias) throws Exception {
		
		List<UserEst> user=userEstRepository.findByAlias(alias);
		if(user != null) {
			return (UserEst) user;
		}else {
			
			throw new Exception("There are no users with that alias.");
		}
		
	}
	
	@Transactional
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

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		//Find User
		UserEst userEst=userEstRepository.findByEmail(email);
		if(userEst != null) {
		//if the user exists I create a list of permissions.
			List<GrantedAuthority> permissions=new ArrayList<>();
			
			GrantedAuthority p1= new SimpleGrantedAuthority("ROLE_"+userEst.getRol());//
			permissions.add(p1);
			//This allows me to save the USER LOG OBJECT, to later be used
			ServletRequestAttributes attr=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			HttpSession session=attr.getRequest().getSession(true);
			session.setAttribute("usersession", userEst);//key + value
			
			User user=new User(userEst.getEmail(), userEst.getPassword(), permissions);
			return user;
		}else {
		
		return null;
		}
	}
}
