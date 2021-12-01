package Login.web.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Login.web.Entity.House;
import Login.web.Entity.Rent;
import Login.web.Repository.HouseRepository;
import Login.web.Repository.RentRepository;
import Login.web.Service.RentService;

@Controller
@RequestMapping("/rent")
public class RentController {
	
	@Autowired
	private HouseRepository houseRepository;
	@Autowired
	private RentService rentService;
	@Autowired
	private RentRepository rentRepository;
	
	@GetMapping("/rent")
	public String rent(ModelMap model) {
		//agregando la lista de todas las casas disponibles
		List<House>houses=houseRepository.findAll();
		model.put("houses", houses);
		return "rent";
	}
	
	@PostMapping("/rent")
	public String rent(ModelMap model,@RequestParam String idCustomer,@RequestParam String idHouse,
			@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateFrom,
			@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateTo) {
		List<House>houses=houseRepository.findAll();
		model.put("houses", houses);
		
		try {
			rentService.rent(idCustomer, idHouse, dateFrom, dateTo);
			model.put("exito","The rent was discharged successfully");
		} catch (Exception e) {
			model.put("error", e.getMessage());
			e.printStackTrace();
		}
		return "rent";
	}
	
	@GetMapping("/list")
	public String list(ModelMap model) {
		List<Rent>rents=rentRepository.findAll();
		model.put("rents", rents);
		return "listRents";
	}
	@GetMapping("/modify/{id}")
	public String modificar(ModelMap modelo,@PathVariable String id) {
		
		try {
			modelo.put("rent", rentRepository.findById(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "rentModify";
		
	}
	@PostMapping("/modify/{id}")
	public String modificar(@PathVariable String id, ModelMap modelo ,@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateFrom,@RequestParam (required = false) @DateTimeFormat(pattern = "yyyy-MM-dd" )Date dateTo) {	
			try {
				rentService.modifyRent(id, dateFrom, dateTo);
				modelo.put("exito", "Successfully modified");
			} catch (Exception e) {
				modelo.put("error", "Could not modify the rent, check the data entered");
				e.printStackTrace();	
			}
			return "redirect:/rent/list";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteDown(ModelMap modelo,@PathVariable String id) {
		try {
			rentService.deleteDown(id);
			modelo.put("exito", "Successfully terminated");
		}catch(Exception e){
        	modelo.put("error", e.getMessage());
        	e.printStackTrace();
        }
		return "redirect:/rent/list";
		
	}
}
