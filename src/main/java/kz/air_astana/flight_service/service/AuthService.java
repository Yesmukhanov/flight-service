package kz.air_astana.flight_service.service;

import kz.air_astana.flight_service.model.request.AuthenticationRequest;
import kz.air_astana.flight_service.model.request.RefreshTokenRequest;
import kz.air_astana.flight_service.model.request.RegisterRequest;
import kz.air_astana.flight_service.model.response.AuthenticationResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

	AuthenticationResponse register(final RegisterRequest registerRequest);

	AuthenticationResponse authentication(AuthenticationRequest authenticationRequest);

	AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
