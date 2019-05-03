package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.UserAttribute;

import java.util.List;

@Repository
public interface UserAttributeRepository extends JpaRepository<UserAttribute, Long> {
    List<UserAttribute> findAll();
}
