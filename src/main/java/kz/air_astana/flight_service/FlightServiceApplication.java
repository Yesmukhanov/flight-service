package kz.air_astana.flight_service;

import kz.air_astana.flight_service.model.entitites.User;
import kz.air_astana.flight_service.model.enums.Role;
import kz.air_astana.flight_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@RequiredArgsConstructor
public class FlightServiceApplication implements CommandLineRunner {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	public static void main(String[] args) {
		SpringApplication.run(FlightServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = User
				.builder()
				.username("moderator")
				.password(passwordEncoder.encode("moderator"))
				.role(Role.MODERATOR)
				.build();

		userRepository.save(user);
	}
}
