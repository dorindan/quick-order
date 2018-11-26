package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.Command;

public interface CommandRepository extends JpaRepository<Command, Long> {
}
