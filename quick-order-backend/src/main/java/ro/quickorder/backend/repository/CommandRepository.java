package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Command;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    Command findByCommandName(String name);

    List<Command> findCommandsByStatus(String status);
}
