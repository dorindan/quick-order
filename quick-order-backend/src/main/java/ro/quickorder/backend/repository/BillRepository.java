package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
}
