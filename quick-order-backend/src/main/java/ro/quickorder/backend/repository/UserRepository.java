package ro.quickorder.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ro.quickorder.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findAllByUsername(String username);

	User findFirstByUsername(String username);

	Optional<User> findByUsername(String username);
}
