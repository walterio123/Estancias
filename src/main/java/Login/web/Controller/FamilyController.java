package Login.web.Controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Login.web.Entity.Family;
import Login.web.Entity.House;
import Login.web.Entity.UserEst;
import Login.web.Repository.FamilyRepository;
import Login.web.Service.FamilyService;
import Login.web.Service.HouseService;
import Login.web.Service.UserEstService;

@Controller
@RequestMapping("/family")
public class FamilyController {
	
	@Autowired
	private HouseService houseService;
	
	@Autowired
	private FamilyService familyService;
	
	@Autowired
	private UserEstService userEstService;
	
	@GetMapping("/register")
	public String registerFamily() {
		
		return "Registration-Family.html";
	}
	@PostMapping("/register")
	public String registerFamily(@RequestParam String userEstId,@RequestParam String name,@RequestParam Integer minAge ,
			@RequestParam Integer maxAge,@RequestParam Integer numSons,@RequestParam String email,ModelMap modelo) {
		try {
			familyService.registerFamily(name, minAge, maxAge, numSons, email, userEstId);
			 modelo.put("exito", "Familia registrada con exito");
	         modelo.put("mensaje", "Registra tu casa");
	         return "inicio.html";
		} catch (Exception e) {
			 modelo.put("error", e.getMessage());
			 modelo.put("name", name);
	         modelo.put("email", email);
	         modelo.put("userEstId", userEstId);
			e.printStackTrace();
			return "Registration-Family.html";
		}	
	}
	@GetMapping("/addHouse")
	public String addHouse(ModelMap model, HttpSession session,@RequestParam String id) {
		//validating that the user who tries to modify the profile is only the one logged in
				UserEst userLogin=(UserEst) session.getAttribute("usersession");
				if(userLogin == null || !userLogin.getId().equals(id)) {
					return "redirect:/inicio";
				}
				try {
					UserEst userEst=userEstService.findUserEstForId(id);
					model.addAttribute("perfil", userEst);
				}catch (Exception e) {
					model.addAttribute("error",e.getMessage());
				}
				
		return "house.html";
	}


	@PostMapping("/addHouse")
	public String addHouse(MultipartFile archivo, ModelMap model, HttpSession session,@RequestParam String id,@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateFrom ,@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateTo ,
			@RequestParam String street,@RequestParam Integer number,@RequestParam String postalCode ,@RequestParam String city,@RequestParam String country, @RequestParam Integer minDays ,@RequestParam Integer maxDays ,@RequestParam Double price ,@RequestParam String tipePlace ) {
			
		
		//validating that the user who tries to modify the profile is only the one logged in
				UserEst userLogin=(UserEst) session.getAttribute("usersession");
				
				try {
					
					if(userLogin == null || !userLogin.getId().equals(id)) {
					return "redirect:/login";
				}
					//check if you have a house, if you have throw error
					Family family=familyService.findForIdUser(userLogin.getId());
					if(family.getHouse() == null) {
						houseService.registerHouse( archivo ,id, street, number, postalCode, city, country, dateFrom, dateTo, minDays, maxDays, price, tipePlace);
						
						model.addAttribute("perfil", userLogin);
						return "redirect:/inicio";
					}else {
						
						model.addAttribute("error","The family already owns a house." );
					}
					
					
				}catch (Exception e) {
					model.addAttribute("error",e.getMessage());
					model.addAttribute("street", street);
					model.addAttribute("number", number);
					
				}
				return "house.html";
		
	}

}
