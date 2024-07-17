package kz.air_astana.flight_service.model.request;

import lombok.Getter;

@Getter
public class AuthenticationRequest {
    private String username;
    private String password;
}
