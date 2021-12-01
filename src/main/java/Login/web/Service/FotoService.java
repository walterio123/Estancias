package Login.web.Service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Login.web.Entity.Foto;
import Login.web.Repository.FotoRepository;



@Service
public class FotoService {

	@Autowired
	private FotoRepository fotoRepository;
	
	@Transactional
	public Foto guardarFoto(MultipartFile archivo) throws Exception{
		//checking that the file is not empty
		if (archivo != null ) {
			try {
				Foto foto=new Foto();
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				
				return fotoRepository.save(foto);
						
			} catch (Exception e) {
				System.err.println(e.getMessage());
				System.out.println("ERROR UPLOADING THE PHOTO");
			}
		}
		//if it is empty we return a null (Serves)
		return null;
	}
	@Transactional
	public  Foto actualizarFoto(String idFoto,MultipartFile archivo) throws Exception{
		
		//checking that the file is not empty
		if (archivo != null) {
			try {
				Foto foto=new Foto();
				//checking if the photo existed
				if (idFoto !=null) {
					//we look for the photo
					Optional<Foto>respuestaOptional=fotoRepository.findById(idFoto);
					foto=respuestaOptional.get();
				}
				
				foto.setMime(archivo.getContentType());
				foto.setNombre(archivo.getName());
				foto.setContenido(archivo.getBytes());
				
				return fotoRepository.save(foto);
						
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		//if it is empty we return a null (Serves)
		return null;
	}
}
