package kz.air_astana.flight_service.repository;

import kz.air_astana.flight_service.model.enitites.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {

	List<Flight> findAllByOrderByArrivalAsc();
}
