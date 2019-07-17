package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Command;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    Command findByStatus(String status);


    Command findByCommandName(String commandName);

}
