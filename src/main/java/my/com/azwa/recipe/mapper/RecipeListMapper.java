package my.com.azwa.recipe.mapper;

import my.com.azwa.recipe.util.ColumnMapper;

public class RecipeListMapper extends ColumnMapper {
  public RecipeListMapper() {
    COLUMN_MAP.put("recipeCode", "recipeCode");
    COLUMN_MAP.put("recipeName", "recipeName");
    COLUMN_MAP.put("typeName", "typeName");
  }
}
