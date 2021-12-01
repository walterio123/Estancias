package Login.web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Login.web.Service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/register")
	public String registerCustomer() {
	return "Registration-Customer.html";
}
	
	@PostMapping("/register")
	public String registerCustomer(@RequestParam String userEstId,@RequestParam String name,@RequestParam String street,
			@RequestParam Integer number,@RequestParam String postalCode,@RequestParam String city, @RequestParam String country,
			@RequestParam String email,ModelMap modelo) {
	
			try {
				customerService.register(name, street, number, postalCode, city, country, email, userEstId);
				 modelo.put("exito", "Cliente registrado con exito");
		         modelo.put("mensaje", "Comienza a alquilar");
				return "inicio.html";
			} catch (Exception e) {
				 modelo.put("error", e.getMessage());
				 modelo.put("name", name);
		         modelo.put("email", email);
		         modelo.put("number", number);
		         modelo.put("postalCode", postalCode);
		         modelo.put("city", city);
		         modelo.put("country", country);
		         modelo.put("email", email);
		         modelo.put("userEstId", userEstId);
				e.printStackTrace();
				return "Registration-Customer.html";
			}
		
	}
}