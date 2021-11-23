package Login.web.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Login.web.Entity.Comment;
import Login.web.Entity.House;
import Login.web.Repository.CommentRepository;
import Login.web.Repository.HouseRepository;

@Service
public class CommentService {
	@Autowired
	private HouseRepository houseRepository;
 
	@Autowired
	private CommentRepository commentRepository;
	
	public void addComment(String description,String idHouse) throws Exception {
		
		Optional<House>resOptional=houseRepository.findById(idHouse);
		if (resOptional.isPresent()) {
			House house=resOptional.get();
			Comment comment=new Comment();
			comment.setDescription(description);
			comment.setHouse(house);
			
			commentRepository.save(comment);
		}else {
			throw new Exception("there are no houses with that id.");
		}
	}
	
	public void modify(String idHouse,String id,String description) throws Exception {
		
		//Find Comment
		Optional<Comment>respOptional=commentRepository.findById(id);
		if (respOptional.isPresent()) {
			Comment comment=respOptional.get();
			//If comment exist,check that the house id is the same as the comment.
			if(comment.getHouse().getId().equals(idHouse)) {
				comment.setDescription(description);
				commentRepository.save(comment);
			}else {
				throw new Exception("The comment does not belong to the house.");
			}
		}else {
			throw new Exception("The comment does not exist.");
		}
	}
	
	
	
}
