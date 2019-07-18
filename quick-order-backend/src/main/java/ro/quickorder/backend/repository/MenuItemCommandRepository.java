package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.MenuItemCommand;

/**
 * @author R. Lupoaie
 */
@Repository
public interface MenuItemCommandRepository extends JpaRepository<MenuItemCommand, Long> {



}
