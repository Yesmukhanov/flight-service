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

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class FlightServiceImpl implements FlightService {
    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    public ResponseEntity<?> addFlight(final FlightDto flightDto) {
        Flight flight = flightMapper.toEntity(flightDto);

        return addFlightToDatabase(flight);
    }

    @Override
    public ResponseEntity<?> changeStatus(final Integer id, final FlightStatus status) {
        Flight flight = flightRepository.findById(id)
                .orElseThrow(() -> new FlightException("FLIGHT NOT FOUND"));

        return updateFlightStatus(flight, status);
    }

    private ResponseEntity<?> updateFlightStatus(final Flight flight, final FlightStatus status) {
        flight.setStatus(status);
        flightRepository.save(flight);

        return ResponseEntity.status(HttpStatus.CREATED).body(flight);

        }


    private ResponseEntity<?> addFlightToDatabase(final Flight flight) {
        flightRepository.save(flight);

        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body(flight);
    }
}
