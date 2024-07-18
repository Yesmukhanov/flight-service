package kz.air_astana.flight_service.service.impl;

import kz.air_astana.flight_service.model.enitites.User;
import kz.air_astana.flight_service.model.enums.Role;
import kz.air_astana.flight_service.model.request.AuthenticationRequest;
import kz.air_astana.flight_service.model.request.RefreshTokenRequest;
import kz.air_astana.flight_service.model.request.RegisterRequest;
import kz.air_astana.flight_service.model.response.AuthenticationResponse;
import kz.air_astana.flight_service.repository.UserRepository;
import kz.air_astana.flight_service.service.AuthService;
import kz.air_astana.flight_service.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public AuthenticationResponse register(final RegisterRequest registerRequest) {
		Optional<User> existingUserOptional = userRepository.findByUsername(registerRequest.getUsername());

		if (existingUserOptional.isPresent()) {
			throw new IllegalStateException("User with this email already exists");
		}

		User user = User
				.builder()
				.username(registerRequest.getUsername())
				.password(passwordEncoder.encode(registerRequest.getPassword()))
				.role(Role.USER)
				.build();
		log.info("REGISTERED NEW USER {} TIME: {}", registerRequest.getUsername(), LocalDateTime.now());
		userRepository.save(user);

		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		return AuthenticationResponse
				.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();

	}

	public AuthenticationResponse authentication(final AuthenticationRequest authenticationRequest) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(),
						authenticationRequest.getPassword()
				)
		);

		User user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(
				() -> new UsernameNotFoundException("USER NOT FOUND"));

		String accessToken = jwtService.generateToken(user);
		String refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		return AuthenticationResponse
				.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();

	}

	public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String username = jwtService.extractUserEmail(refreshTokenRequest.getRefreshToken());
		User user = userRepository.findByUsername(username).orElseThrow();

		if (jwtService.isTokenValid(refreshTokenRequest.getRefreshToken(), user)) {

			String accessToken = jwtService.generateToken(user);
			String refreshToken = refreshTokenRequest.getRefreshToken();

			return AuthenticationResponse
					.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken)
					.build();
		}

		return null;
	}
}
