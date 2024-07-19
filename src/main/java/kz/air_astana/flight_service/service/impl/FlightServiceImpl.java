package kz.air_astana.flight_service.service.impl;

import kz.air_astana.flight_service.exception.FlightException;
import kz.air_astana.flight_service.mapper.FlightMapper;
import kz.air_astana.flight_service.model.entitites.Flight;
import kz.air_astana.flight_service.model.enums.FlightStatus;
import kz.air_astana.flight_service.model.request.FlightRequest;
import kz.air_astana.flight_service.repository.FlightRepository;
import kz.air_astana.flight_service.service.FlightService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
	private final FlightRepository flightRepository;
	private final FlightMapper flightMapper;
	private final AuthenticationManager authenticationManager;

	@Override
	public ResponseEntity<?> addFlight(final FlightRequest flightRequest) {
		if (isValidFlightStatus(flightRequest.getFlightStatus())) {
			throw new FlightException("STATUS NOT VALID");
		}
		checkOriginAndDestination(flightRequest.getOrigin(), flightRequest.getDestination());
		Flight flight = Flight
				.builder()
				.origin(flightRequest.getOrigin())
				.destination(flightRequest.getDestination())
				.departure(flightRequest.getDeparture())
				.arrival(flightRequest.getArrival())
				.build();
		return addFlightToDatabase(flight, flightRequest.getFlightStatus());
	}

	private void checkOriginAndDestination(final String origin, final String destination) {
		if (origin.isEmpty() || destination.isEmpty()) {
			throw new FlightException("Origin and Destination must be not empty");
		}
	}

	@Override
	public ResponseEntity<?> changeStatus(final Integer id, final String status) {
		Flight flight = flightRepository.findById(id)
				.orElseThrow(() -> new FlightException("FLIGHT NOT FOUND"));

		if (isValidFlightStatus(status)) {
			throw new FlightException("STATUS NOT VALID");
		}
		return updateFlightStatus(flight, status);
	}

	public ResponseEntity<?> getAllFlights(String origin, String destination) {
		List<Flight> flights = getFilteredFlights(origin, destination);
		return ResponseEntity.ok(flights);
	}

	private List<Flight> getFilteredFlights(String origin, String destination) {
		if (Objects.nonNull(origin) && Objects.nonNull(destination)) {
			return flightRepository.findAllByOriginAndDestinationOrderByArrivalAsc(origin, destination);
		} else if (Objects.nonNull(origin)) {
			return flightRepository.findAllByOriginOrderByArrivalAsc(origin);
		} else if (Objects.nonNull(destination)) {
			return flightRepository.findAllByDestinationOrderByArrivalAsc(destination);
		} else {
			return flightRepository.findAllByOrderByArrivalAsc();
		}
	}

	private ResponseEntity<?> updateFlightStatus(final Flight flight, final String status) {
		flight.setStatus(FlightStatus.valueOf(status));
		flightRepository.save(flight);

		log.info("USER {} CHANGED STATUS TIME: {}", getUsername(), LocalDateTime.now());

		return ResponseEntity.status(HttpStatus.CREATED).body(flight);

	}


	private ResponseEntity<?> addFlightToDatabase(final Flight flight, final String flightStatus) {
		flight.setStatus(FlightStatus.valueOf(flightStatus));
		flightRepository.save(flight);
		log.info("USER {} ADDED NEW FLIGHT TIME: {}", getUsername(), LocalDateTime.now());

		return ResponseEntity.status(HttpStatusCode.valueOf(201)).body(flight);
	}

	private boolean isValidFlightStatus(final String status) {
		try {
			FlightStatus.valueOf(status);
			return false;
		} catch (IllegalArgumentException e) {
			return true;
		}
	}

	private String getUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return auth.getName();
	}
}
