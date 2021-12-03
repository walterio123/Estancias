package Login.web.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

import Login.web.Entity.House;
import Login.web.Service.CommentService;
import Login.web.Service.HouseService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private HouseService houseService;
	
	@GetMapping("/addComment/{id}")
	public String addComment(ModelMap model,@PathVariable String id) {
		House house;
		try {
			house = houseService.findForId(id);
			model.put("house", house);
			return "comment";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/house/allHouseUser";
		}
	
	}
	@PostMapping("/addComment/{id}")
	public String addComment(ModelMap model,@RequestParam String id,@RequestParam String description) {
		try {
			commentService.addComment(id, description);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:/house/allHouseUser";
		
	
	}
	
	@GetMapping("/comment/{id}")
	public String commentHouse(@PathVariable String id){
		// looking for the house that owns the comment
		House house;
		try {
			house = houseService.findForId(id);
			// if the house has no comment
			if (house.getComment()== null) {
				throw new Exception("User has no comment to show");
			}
			String comment= house.getComment().getDescription();
	
			return comment;
		} catch (Exception ex) {
			 String st="Not Message";
			return st;
		}	
	}
}
