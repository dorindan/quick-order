package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
