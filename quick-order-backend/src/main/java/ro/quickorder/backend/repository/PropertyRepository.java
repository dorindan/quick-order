package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.Property;
import ro.quickorder.backend.model.PropertyName;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    Property findByName(PropertyName name);
}
