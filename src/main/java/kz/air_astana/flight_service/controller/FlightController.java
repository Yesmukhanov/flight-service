package kz.air_astana.flight_service.controller;

import kz.air_astana.flight_service.model.request.FlightRequest;
import kz.air_astana.flight_service.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flight")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<?> addFlight(@RequestBody final FlightRequest flight) {
        return flightService.addFlight(flight);
    }

    @PutMapping("/{flightId}")
    public ResponseEntity<?> updateFlight(@PathVariable final Integer flightId,
                                          @RequestParam(name = "status") final String status) {
        return flightService.changeStatus(flightId, status);
    }

    @GetMapping
    public ResponseEntity<?> getAllFlights() {
        return flightService.getAllFlights();
    }
}
