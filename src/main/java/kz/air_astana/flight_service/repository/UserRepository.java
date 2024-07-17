package kz.air_astana.flight_service.repository;

import kz.air_astana.flight_service.model.enitites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUsername(String username);
}
