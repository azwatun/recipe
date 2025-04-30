package my.com.azwa.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import my.com.azwa.recipe.model.RecipeType;

public interface RecipeTypeRepository extends JpaRepository<RecipeType, Long> {
    List<RecipeType> findByTypeCode(String typeCode);
}
