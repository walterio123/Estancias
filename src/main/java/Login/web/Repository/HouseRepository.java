package Login.web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import Login.web.Entity.Family;
import Login.web.Entity.House;



public interface HouseRepository extends JpaRepository<House, String>{
	@Query("SELECT f.house FROM  Family f WHERE f.id = :id ")
	public List<House> findHousesForUser(@Param("id") String id);
	
	@Query("SELECT f.house FROM  Family f WHERE f.alta IS NOT FALSE")
	public List<House> findHousesForUserAlta();
}
