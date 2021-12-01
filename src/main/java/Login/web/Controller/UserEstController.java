package Login.web.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Login.web.Entity.UserEst;
import Login.web.Repository.UserEstRepository;
import Login.web.Service.UserEstService;

@Controller
@RequestMapping("/user")
public class UserEstController {

	@Autowired
	private UserEstService userEstService;
	
	//@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
	@GetMapping("/editar-perfil")
	public String editProfile(HttpSession session, @RequestParam String id, ModelMap model) {
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
		return "profile.html";	
	}
	//@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
	@PostMapping("/editar-perfil")
	public String editProfile(ModelMap modelo, HttpSession session,@RequestParam String id,@RequestParam String alias,@RequestParam String email, @RequestParam String password) {
		UserEst userEst=null;
		try {
			//validating that the user who tries to modify the profile is only the one logged in
			UserEst userLogin=(UserEst) session.getAttribute("usersession");
			if(userLogin == null || !userLogin.getId().equals(id)) {
				return "redirect:/login";
			}
			userEst=userEstService.findUserEstForId(id);
			userEstService.modify(id, alias, email, password);
			session.setAttribute("usersession", userEst);
			return "redirect:/inicio";
		} catch (Exception e) {
			modelo.put("perfil", userEst);
			modelo.put("error", e.getMessage());
			return "profile";
		}
		
	}
}


