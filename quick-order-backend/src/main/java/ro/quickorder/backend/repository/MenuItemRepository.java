package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
}
