package ro.quickorder.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.quickorder.backend.model.Ingredient;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Ingredient findFirstByName(String name);

    @Query(value ="Select i From Ingredient i order by i.name")
    List<Ingredient> findAllSorted();

    boolean existsIngredientByName(String name);
}
