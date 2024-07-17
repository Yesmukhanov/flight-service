package kz.air_astana.flight_service.model.enitites;

import jakarta.persistence.*;
import kz.air_astana.flight_service.model.enums.FlightStatus;
import lombok.*;

import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_flights")
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	@Size(max = 256)
	private String origin;

	@Column(nullable = false)
	@Size(max = 256)
	private String destination;

	@Column(nullable = false)
	private OffsetDateTime departure;

	@Column(nullable = false)
	private OffsetDateTime arrival;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private FlightStatus status;
}