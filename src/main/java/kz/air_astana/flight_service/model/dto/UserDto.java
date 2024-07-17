package kz.air_astana.flight_service.model.dto;

import kz.air_astana.flight_service.model.enums.Role;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	@NotNull
	private Long id;

	@NotNull
	@Size(max = 256)
	private String username;

	private Role role;
}
