package Login.web.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Login.web.Entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, String>{

}
