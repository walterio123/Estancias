package Login.web.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



import Login.web.Entity.UserEst;

@Repository
public interface UserEstRepository extends JpaRepository<UserEst, String>{

	@Query("SELECT c FROM UserEst c ")
	public List<UserEst>userList();
	
	@Query("Select a From UserEst a WHERE a.id= :id")
	public void deleteById(String id);
	
	@Query("SELECT a FROM UserEst a WHERE a.alias like :alias%")
	public List<UserEst> findByAlias(@Param("alias") String alias); 
	
	@Query("SELECT a FROM UserEst a WHERE a.email like :email%")
	public UserEst findByEmail(@Param("email") String email); 
	
	@Query("SELECT c FROM UserEst c WHERE c.discharge IS NOT NULL")//Revisar
	public List<UserEst>listUserRegistered();
}
