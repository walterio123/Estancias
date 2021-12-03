package Login.web.Controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



import Login.web.Entity.Family;
import Login.web.Entity.House;
import Login.web.Entity.UserEst;
import Login.web.Repository.HouseRepository;
import Login.web.Service.FamilyService;
import Login.web.Service.HouseService;

@Controller
@RequestMapping("/house")
public class HouseController {
	
	@Autowired
	private HouseService houseService;
	@Autowired
	private FamilyService familyService;
	@Autowired
	private HouseRepository houseRepository;
	
	@GetMapping("/all")
	public String listHouse(Model model){
		try {
			List<House>house=houseService.listAllHouse();
			model.addAttribute("house", house);
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		
		return "ListOfHouse";
	}
	
	
	@GetMapping("/allHouseUser")
	public String listHouseOfUser(Model model,HttpSession session){
		// retrieving the logged in user if it does not redirect to home
		UserEst login=(UserEst) session.getAttribute("usersession");
		if(login == null) {
			return "redirect:/login";
		}
		// with the user search for the family
		try {
			Family family=familyService.findForIdUser(login.getId());
			// with the family look for the house
			List<House>house=houseService.findHouseForUser(family.getId());
			model.addAttribute("house", house);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return "HouseOfFamily";
	}
	
	@GetMapping("/modify/{id}")
	public String modifyHouse(@PathVariable String id,ModelMap modelo) {
		 
		try {
			modelo.put("house", houseService.findForId(id));
		} catch (Exception e) {
		
			e.printStackTrace();
		}
		return "houseModify";
	}
	
	@PostMapping("/modify/{id}")
	public String modificarHouse(MultipartFile archivo,@RequestParam String id,  ModelMap modelo ,@RequestParam String street,@RequestParam Integer number,@RequestParam String postalCode,@RequestParam String city,@RequestParam String country,@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateFrom ,@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateTo,
			@RequestParam Integer minDays,@RequestParam Integer maxDays,@RequestParam Double price,@RequestParam String tipePlace) {	
			try {
				houseService.modifyHouse(archivo, id, street, number, postalCode, city, country, dateFrom, dateTo, minDays, maxDays, price, tipePlace);
				modelo.put("exito", "Successfully modified");
			} catch (Exception e) {
				modelo.put("error", "The rental could not be modified, check the data entered");
				e.printStackTrace();	
			}
			return "redirect:/house/allHouseUser";	
		
	}
	@GetMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        try{
        	
            House house=houseService.findForId(id);
            house.setAlta(false);
            houseRepository.save(house);
        }catch(Exception e){
        	e.printStackTrace();         
        }
        return "redirect:/house/allHouseUser";
    }
	
	@GetMapping("/alta/{id}")
    public String darAlta(@PathVariable String id){
        try{
           houseService.darAlta(id);          
        }catch(Exception e){
        	e.printStackTrace();
        }
        return "redirect:/house/allHouseUser";
    }
}
