package kz.air_astana.flight_service.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlightRequest {

	private Long id;

	@NotNull(message = "Origin cannot be null")
	@NotBlank(message = "Origin cannot be blank")
	@Size(max = 256, message = "Origin cannot be longer than 256 characters")
	private String origin;

	@NotNull(message = "Destination cannot be null")
	@NotBlank(message = "Destination cannot be blank")
	@Size(max = 256, message = "Destination cannot be longer than 256 characters")
	private String destination;

	@NotNull(message = "Departure time cannot be null")
	private OffsetDateTime departure;

	@NotNull(message = "Arrival time cannot be null")
	private OffsetDateTime arrival;

	@NotNull(message = "Flight status cannot be null")
	@NotBlank(message = "Flight status cannot be blank")
	private String flightStatus;
}
