package my.com.azwa.recipe.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import my.com.azwa.recipe.dto.PaginationRequestDTO;
import my.com.azwa.recipe.dto.PaginationResponseDTO;
import my.com.azwa.recipe.dto.RecipeDetailDTO;
import my.com.azwa.recipe.dto.RecipeListDTO;
import my.com.azwa.recipe.dto.RecipeRequest;
import my.com.azwa.recipe.dto.RecipeSearchDTO;
import my.com.azwa.recipe.model.RecipeType;

public interface IRecipeService {

    public List<RecipeType> getRecipeTypes();

    public List<RecipeDetailDTO> getRecipes(Long recipeSeqno);

    public RecipeDetailDTO getRecipeDetails(Long recipeSeqno);

    public byte[] getImageRecipe(Long seqno);

    public void updateSaveImage(Long recipeSeqno, MultipartFile file, Long userId) throws IOException;

    public void saveRecipe(RecipeRequest recipeRequest);

    public void deleteRecipe(Long recipeSeqno, Long userId);

    public void removeRecipe(Long recipeSeqno);

    public List<RecipeListDTO> getRecipeList(RecipeSearchDTO search, PaginationRequestDTO paginationRequestDTO);

    public PaginationResponseDTO getRecipeListPage(RecipeSearchDTO search, PaginationRequestDTO paginationRequestDTO);
}
