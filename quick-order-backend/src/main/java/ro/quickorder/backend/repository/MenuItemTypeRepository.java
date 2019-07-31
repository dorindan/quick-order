package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Ingredient;
import ro.quickorder.backend.model.MenuItemType;

import java.util.List;

@Repository
public interface MenuItemTypeRepository extends JpaRepository<MenuItemType, Long> {

    @Query(value ="Select m From MenuItemType m order by m.type")
    List<MenuItemType> findAllSorted();

    MenuItemType findByType(String type);

    boolean existsMenuItemTypeByType(String type);
}
