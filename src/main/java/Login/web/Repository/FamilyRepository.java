package Login.web.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Login.web.Entity.Family;




public interface FamilyRepository extends JpaRepository<Family, String>{

	@Query("SELECT c FROM Family c WHERE c.userEst.id= :id ")
	public Family findForIdUser(@Param("id") String id);
}
