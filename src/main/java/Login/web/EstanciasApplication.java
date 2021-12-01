package Login.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import Login.web.Service.UserEstService;

@SpringBootApplication
public class EstanciasApplication {
	
	@Autowired
	private UserEstService userEstService;

	public static void main(String[] args) {
		SpringApplication.run(EstanciasApplication.class, args);
	}
	
	  @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	                .userDetailsService(userEstService)
	                .passwordEncoder(new BCryptPasswordEncoder());

	    }

}
