package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.TableFood;

@Repository
public interface TableFoodRepository extends JpaRepository<TableFood, Long> {
    TableFood findByTableNr(int tableNr);
}
