package kz.air_astana.flight_service.mapper;

import kz.air_astana.flight_service.model.dto.FlightDto;
import kz.air_astana.flight_service.model.entitites.Flight;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FlightMapper {


	Flight toEntity(FlightDto flightDto);

	FlightDto toDto(Flight flight);

	List<FlightDto> toListDto(List<Flight> flights);
}
