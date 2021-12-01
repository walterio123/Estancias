package Login.web.Controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import Login.web.Entity.House;
import Login.web.Service.HouseService;

@Controller
@RequestMapping("/foto")
public class FotoController {

	@Autowired
	private HouseService houseService;
	
	@GetMapping("/house/{id}")
	public ResponseEntity<byte[]> fotoHouse(@PathVariable String id){
		//buscando la casa dueña de la foto
		House house;
		try {
			house = houseService.findForId(id);
			//si la casa no tiene foto
			if (house.getFoto()== null) {
				throw new Exception("User has no photo to show");
			}
			byte[] foto= house.getFoto().getContenido();
			
			//creando las cabeceras necesarias para devolver una imagen
			//para indicarle al navegador que va a mostrar una foto
			HttpHeaders headers=new HttpHeaders();
			headers.setContentType(MediaType.IMAGE_JPEG);
			
			//devolviendo un response entity
			return new ResponseEntity<>(foto, headers , HttpStatus.OK);
		} catch (Exception ex) {
			 Logger.getLogger(FotoController.class.getName()).log(Level.SEVERE, null, ex);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
}
