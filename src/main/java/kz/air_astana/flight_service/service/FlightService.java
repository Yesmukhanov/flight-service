package kz.air_astana.flight_service.service;

import kz.air_astana.flight_service.model.dto.FlightDto;
import kz.air_astana.flight_service.model.enitites.Flight;
import kz.air_astana.flight_service.model.enums.FlightStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface FlightService {


    ResponseEntity<?> addFlight(final FlightDto flight);

    ResponseEntity<?> changeStatus(final Integer id, final String status);
}
