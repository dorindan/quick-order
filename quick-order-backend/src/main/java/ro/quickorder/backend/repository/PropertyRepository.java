package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
