package kz.air_astana.flight_service.service.impl;

import kz.air_astana.flight_service.exception.FlightException;
import kz.air_astana.flight_service.mapper.FlightMapper;
import kz.air_astana.flight_service.model.enitites.Flight;
import kz.air_astana.flight_service.model.enums.FlightStatus;
import kz.air_astana.flight_service.model.request.FlightRequest;
import kz.air_astana.flight_service.repository.FlightRepository;
import kz.air_astana.flight_service.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public ResponseEntity<?> addFlight(final FlightRequest flightRequest) {
        if(isValidFlightStatus(flightRequest.getFlightStatus())) {
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

    private void checkOriginAndDestination(String origin, String destination) {
        if(origin.isEmpty() || destination.isEmpty()) {
            throw new FlightException("Origin and Destination must be not empty");
        }
    }

    @Override
    public ResponseEntity<?> changeStatus(final Integer id, final String  status) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightException("FLIGHT NOT FOUND"));

        if(isValidFlightStatus(status)) {
            throw new FlightException("STATUS NOT VALID");
        }
        return updateFlightStatus(flight, status);
    }

    @Override
    public ResponseEntity<?> getAllFlights() {
        return ResponseEntity.ok(flightMapper.toListDto(flightRepository.findAllByOrderByArrivalAsc()));
    }

    private ResponseEntity<?> updateFlightStatus(final Flight flight, final String status) {
        flight.setStatus(FlightStatus.valueOf(status));
        flightRepository.save(flight);

        return ResponseEntity.status(HttpStatus.CREATED).body(flight);

        }


    private ResponseEntity<?> addFlightToDatabase(final Flight flight, final String flightStatus) {
        flight.setStatus(FlightStatus.valueOf(flightStatus));
        flightRepository.save(flight);

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
}
