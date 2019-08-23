package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ro.quickorder.backend.model.MenuItemCommand;

import java.util.List;

/**
 * @author R. Lupoaie
 */
@Repository
public interface MenuItemCommandRepository extends JpaRepository<MenuItemCommand, Long> {

    @Transactional
    void deleteByCommandId(String id);
}
