package my.com.azwa.recipe.dto;

import java.time.LocalDateTime;

public record RecipeDetailDTO(
        Long recipeSeqno,
        String recipeCode,
        String recipeName,
        String recipeDesc,
        String recipeIngredient,
        String recipeInstruction,
        String typeCode,
        String typeName,
        LocalDateTime createdDate,
        LocalDateTime updatedDate) {
}
