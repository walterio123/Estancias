package Login.web.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import Login.web.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	@Query("SELECT c FROM User c ")
	public List<User>userList();
	
	@Query("Select a From User a WHERE a.id= :id")
	public void deleteById(String id);
	
	@Query("SELECT a FROM User a WHERE a.alias like :alias%")
	public List<User> findByAlias(@Param("alias") String alias); 
	
	@Query("SELECT c FROM User c WHERE c.discharge IS NOT NULL")//Revisar
	public List<User>listUserRegistered();
}
