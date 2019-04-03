package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.MenuItem;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {


    MenuItem findByName(String name);

    @Query(value = "Select distinct m From MenuItem m left join fetch m.ingredients")
    List<MenuItem> findAll();
}
