package my.com.azwa.recipe.dto;

public record RecipeSearchDTO(
        Long recipeSeqno,
        String recipeCode,
        String recipeName,
        String typeName) {

}
