package kz.air_astana.flight_service.controller;

import kz.air_astana.flight_service.model.request.AuthenticationRequest;
import kz.air_astana.flight_service.model.request.RefreshTokenRequest;
import kz.air_astana.flight_service.model.request.RegisterRequest;
import kz.air_astana.flight_service.model.response.AuthenticationResponse;
import kz.air_astana.flight_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody final RegisterRequest registerRequest) {
		try {
			AuthenticationResponse response = authService.register(registerRequest);
			return ResponseEntity.ok(response);
		} catch (IllegalStateException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User with this username already exists");
		}
	}

	@PostMapping("/authentication")
	public ResponseEntity<AuthenticationResponse> authentication(
			@RequestBody final AuthenticationRequest authenticationRequest) {

		return ResponseEntity.ok(authService.authentication(authenticationRequest));
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthenticationResponse> refresh(@RequestBody final RefreshTokenRequest refreshTokenRequest) {

		return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
	}

}
