package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.quickorder.backend.model.Property;

public interface PropertyRepository extends JpaRepository<Property, Long> {

    public Property findByNumeRestaurant(String nume_restaurant);
}
