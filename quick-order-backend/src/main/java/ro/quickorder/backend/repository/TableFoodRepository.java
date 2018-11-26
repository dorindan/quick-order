package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.TableFood;

public interface TableFoodRepository extends JpaRepository<TableFood, Long> {
}
