package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Command;

import java.util.List;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    @Query(value = "Select c FROM Command c left join fetch c.menuItemCommands where c.commandName = :commandName")
    Command findByCommandNameWithItems(String commandName);

    Command findByCommandName(String commandName);

    List<Command> findCommandsByStatus(String status);
}