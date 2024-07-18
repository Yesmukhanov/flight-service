package kz.air_astana.flight_service.service.impl;

import kz.air_astana.flight_service.exception.FlightException;
import kz.air_astana.flight_service.mapper.FlightMapper;
import kz.air_astana.flight_service.model.dto.FlightDto;
import kz.air_astana.flight_service.model.enitites.Flight;
import kz.air_astana.flight_service.model.enums.FlightStatus;
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
    public ResponseEntity<?> addFlight(final FlightDto flightDto) {
        Flight flight = flightMapper.toEntity(flightDto);

        if(isValidFlightStatus(flightDto.getFlightStatus())) {
            throw new FlightException("STATUS NOT VALID");
        }
        return addFlightToDatabase(flight, flightDto.getFlightStatus());
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

    private ResponseEntity<?> updateFlightStatus(final Flight flight, final String status) {
        flight.setStatus(FlightStatus.valueOf(status));
        flightRepository.save(flight);

        return ResponseEntity.status(HttpStatus.CREATED).body(flight);

        }


    private ResponseEntity<?> addFlightToDatabase(final Flight flight, final String flightStatus) {
        flight.setStatus(FlightStatus.valueOf(flightStatus));
        flightRepository.save(flight);

        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(flight);
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
