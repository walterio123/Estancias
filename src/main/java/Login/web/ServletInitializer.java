package Login.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import Login.web.Service.UserEstService;

public class ServletInitializer extends SpringBootServletInitializer {
	
	@Autowired
	private UserEstService userEstService;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EstanciasApplication.class);
	}

}
