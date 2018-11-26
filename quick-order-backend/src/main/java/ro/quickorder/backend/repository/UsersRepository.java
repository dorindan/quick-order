package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
