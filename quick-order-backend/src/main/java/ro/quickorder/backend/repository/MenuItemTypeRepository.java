package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.MenuItemType;

public interface MenuItemTypeRepository extends JpaRepository<MenuItemType,Long> {
}
