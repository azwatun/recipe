package my.com.azwa.recipe.dto;

public record RecipeListDTO(
        Long recipeSeqno,
        String recipeCode,
        String recipeName,
        String typeName,
        String recipeDesc) {

}
