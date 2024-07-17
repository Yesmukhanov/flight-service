package kz.air_astana.flight_service.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService {

	String generateToken(UserDetails userDetails);

	String extractUserEmail(String token);

	<T> T extractClaim(String token, Function<Claims, T> claimsResolver);

	Claims extractAllClaims(String token);

	boolean isTokenValid(String token, UserDetails userDetails);

	String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);

	Date extractExpiration(String token);

}
