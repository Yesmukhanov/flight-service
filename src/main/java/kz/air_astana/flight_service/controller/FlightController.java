package kz.air_astana.flight_service.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import kz.air_astana.flight_service.model.request.FlightRequest;
import kz.air_astana.flight_service.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flight")
@Api(tags = "Flight Management", value = "Endpoints for managing flights")
public class FlightController {

    private final FlightService flightService;

    @PostMapping
    @ApiOperation(value = "Add a new flight")
    public ResponseEntity<?> addFlight(@RequestBody final FlightRequest flight) {
        return flightService.addFlight(flight);
    }

    @PutMapping("/{flightId}")
    @ApiOperation(value = "Update flight status by ID")
    public ResponseEntity<?> updateFlight(
            @ApiParam(value = "Flight ID", example = "1") @PathVariable final Integer flightId,
            @ApiParam(value = "New flight status", example = "ON_TIME") @RequestParam(name = "status") final String status) {
        return flightService.changeStatus(flightId, status);
    }

    @GetMapping
    @ApiOperation(value = "Get all flights")
    public ResponseEntity<?> getAllFlights(
            @RequestParam(required = false) final String origin,
            @RequestParam(required = false) final String destination) {

        return ResponseEntity.ok(flightService.getAllFlights(origin, destination));
    }
}
