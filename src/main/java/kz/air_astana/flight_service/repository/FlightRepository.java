package kz.air_astana.flight_service.repository;

import kz.air_astana.flight_service.model.entitites.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

	@Query("SELECT f FROM Flight f ORDER BY f.arrival ASC")
	List<Flight> findAllByOrderByArrivalAsc();

	@Query("SELECT f FROM Flight f WHERE f.origin = :origin ORDER BY f.arrival ASC")
	List<Flight> findAllByOriginOrderByArrivalAsc(@Param("origin") String origin);

	@Query("SELECT f FROM Flight f WHERE f.destination = :destination ORDER BY f.arrival ASC")
	List<Flight> findAllByDestinationOrderByArrivalAsc(@Param("destination") String destination);

	@Query("SELECT f FROM Flight f WHERE f.origin = :origin AND f.destination = :destination ORDER BY f.arrival ASC")
	List<Flight> findAllByOriginAndDestinationOrderByArrivalAsc(@Param("origin") String origin, @Param("destination") String destination);
}
