package Login.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import Login.web.Entity.Rent;
@Repository
public interface RentRepository extends JpaRepository<Rent, String> {
	
	@Query("SELECT c FROM Rent c WHERE c.id= :id AND c.alta IS NOT FALSE")
	public Rent searchById(@Param("id") String id);
	
	@Query("SELECT c FROM Rent c WHERE c.alta IS NOT FALSE")
	public List<Rent> searchOnlyHigh();
}
