package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.MenuItemType;

@Repository
public interface MenuItemTypeRepository extends JpaRepository<MenuItemType, Long> {

    MenuItemType findByType(String type);
}
