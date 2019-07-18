package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Command;
import ro.quickorder.backend.model.User;
import ro.quickorder.backend.model.enumeration.CommandStatus;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {

    @Query(value = "Select c FROM Command c where :user member of c.users and c.status = :status ")
    Command findActiveByUser(User user, CommandStatus status);

    @Query(value = "Select c FROM Command c left join fetch c.menuItemCommands ")
    Command findByCommandNameWithItems(String commandName);

}
