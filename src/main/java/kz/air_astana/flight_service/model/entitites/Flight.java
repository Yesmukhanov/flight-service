package kz.air_astana.flight_service.model.entitites;

import jakarta.persistence.*;
import kz.air_astana.flight_service.model.enums.FlightStatus;
import lombok.*;

import javax.validation.constraints.NotEmpty;
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
	@NotEmpty
	private String origin;

	@Column(nullable = false)
	@Size(max = 256)
	@NotEmpty
	private String destination;

	@Column(nullable = false)
	@NotEmpty
	private OffsetDateTime departure;

	@Column(nullable = false)
	@NotEmpty
	private OffsetDateTime arrival;

	@Column(nullable = false)
	@NotEmpty
	@Enumerated(EnumType.STRING)
	private FlightStatus status;
}