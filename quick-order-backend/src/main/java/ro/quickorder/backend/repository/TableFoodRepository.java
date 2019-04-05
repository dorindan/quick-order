package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.TableFood;
import sun.awt.SunHints;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TableFoodRepository extends JpaRepository<TableFood, Long> {

    TableFood findByTableNr(int tableNr);

    @Query(value = "Select t From TableFood t where t.active=true ORDER BY t.tableNr ASC")
    List<TableFood> findAll();
}
