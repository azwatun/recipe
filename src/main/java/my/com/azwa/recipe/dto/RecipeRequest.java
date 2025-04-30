package my.com.azwa.recipe.dto;

public record RecipeRequest(
                Long recipeSeqno,
                String recipeCode,
                String recipeName,
                String recipeDesc,
                String recipeIngredient,
                String recipeInstruction,
                Long typeSeqno,
                Long userId) {

}
