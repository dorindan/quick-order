package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.CommandMenuItem;

/**
 * @author R. Lupoaie
 */
@Repository
public interface CommandMenuItemRepository  extends JpaRepository<CommandMenuItem, Long> {
}
