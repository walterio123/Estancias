package Login.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Login.web.Entity.House;

public interface HouseRepository extends JpaRepository<House, String>{

}
