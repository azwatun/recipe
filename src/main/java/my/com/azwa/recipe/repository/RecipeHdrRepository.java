package my.com.azwa.recipe.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import my.com.azwa.recipe.model.RecipeHdr;

public interface RecipeHdrRepository extends JpaRepository<RecipeHdr, Long> {
    List<RecipeHdr> findByRecipeCode(String recipeCode);

    RecipeHdr findByRecipeSeqno(Long recipeSeqno);
}
