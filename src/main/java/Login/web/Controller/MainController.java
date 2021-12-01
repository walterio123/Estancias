package Login.web.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Login.web.Entity.UserEst;
import Login.web.Service.UserEstService;

@Controller
@RequestMapping("/")
public class MainController {
	
	
	@Autowired
	private UserEstService userEstService;
	
	@GetMapping("/")
	private String index() {
		return "index.html";
	}
	
	@PreAuthorize("hasAnyRole('ROLE_USUARIO')")
    @GetMapping("/inicio")
    public String inicio(ModelMap modelo) {
    	
    	
    	
        return "inicio.html";
    }
    @GetMapping("/logearse")
    public String logearse(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
    	return "login.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {
            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente.");
        }
        return "login.html";
    }

    @GetMapping("/registro")
    public String irARegistrar(ModelMap modelo){
        try {
        UserEst userEst=new UserEst();
        modelo.addAttribute("usuario", userEst);
        return "Registration.html";
        } catch (Exception e) {
            return "index.html";
        }
        
    }

    @PostMapping("/registro")
    public String registrar(ModelMap modelo, @RequestParam(required=false)String alias, @RequestParam(required=false)String email,
            @RequestParam(required=false)String password){
    	
        try {
           userEstService.register(alias, email, password); 
           modelo.put("exito", "Bienvenido a Estancias.com");
           modelo.put("mensaje", "Inicia Sesión por favor");
           return "login.html";
        } catch (Exception e) {
            modelo.put("error", e.getMessage());
            modelo.put("alias", alias);
            modelo.put("email", email);
            modelo.put("clave", password);
            return "Registration.html";
            
            
        }
       
        
    }

	
}
