package kz.air_astana.flight_service.model.dto;


import kz.air_astana.flight_service.model.enums.FlightStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightDto {

	private Long id;

	@NotNull
	@Size(max = 256)
	private String origin;

	@NotNull
	@Size(max = 256)
	private String destination;

	private OffsetDateTime departure;

	private OffsetDateTime arrival;

	private FlightStatus status;
}
