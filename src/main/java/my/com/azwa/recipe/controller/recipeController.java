package my.com.azwa.recipe.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.com.azwa.recipe.dto.PaginationRequestDTO;
import my.com.azwa.recipe.dto.PaginationResponseDTO;
import my.com.azwa.recipe.dto.RecipeDetailDTO;
import my.com.azwa.recipe.dto.RecipeListDTO;
import my.com.azwa.recipe.dto.RecipeRequest;
import my.com.azwa.recipe.dto.RecipeSearchDTO;
import my.com.azwa.recipe.model.RecipeType;
import my.com.azwa.recipe.service.IRecipeService;

@RestController
@RequestMapping("/api/v1/recipe")
@Slf4j
@AllArgsConstructor
public class recipeController {

    private final IRecipeService recipeService;

    /************* ✨ Windsurf Command ⭐ *************/
    /**
     * Retrieves a list of recipes based on the provided search criteria and
     * pagination parameters.
     *
     * @param recipeSeqno   Optional sequence number of the recipe to filter.
     * @param recipeCode    Optional code of the recipe to filter.
     * @param recipeName    Optional name of the recipe to filter.
     * @param typeName      Optional type name of the recipe to filter.
     * @param sort          Optional field to sort the results by.
     * @param sortDirection Optional direction of sorting, either "asc" or "desc".
     * @param page          Optional page number for pagination.
     * @param size          Optional number of records per page for pagination.
     * @return A list of RecipeListDTO objects that match the search and pagination
     *         criteria.
     *         URL: http://localhost:8080/api/v1/recipe/
     */

    /******* ec518575-3975-4dc3-8831-e2bd97f2c6d7 *******/
    @GetMapping("/")
    public List<RecipeListDTO> getRecipeList(@RequestParam(required = false) Long recipeSeqno,
            @RequestParam(required = false) String recipeCode,
            @RequestParam(required = false) String recipeName,
            @RequestParam(required = false) String typeName,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long size) {
        log.info("ENTRY", "getRecipeList");
        PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(sort, sortDirection, page, size);
        RecipeSearchDTO search = new RecipeSearchDTO(recipeSeqno, recipeCode, recipeName, typeName);
        return recipeService.getRecipeList(search, paginationRequestDTO);
    }

    /**
     * Retrieves a list of recipes based on the provided search criteria and
     * pagination parameters.
     *
     * @param recipeSeqno   Optional sequence number of the recipe to filter.
     * @param recipeCode    Optional code of the recipe to filter.
     * @param recipeName    Optional name of the recipe to filter.
     * @param typeName      Optional type name of the recipe to filter.
     * @param sort          Optional field to sort the results by.
     * @param sortDirection Optional direction of sorting, either "asc" or "desc".
     * @param page          Optional page number for pagination.
     * @param size          Optional number of records per page for pagination.
     * @return A PaginationResponseDTO that contains a list of RecipeListDTO objects
     *         that match the search and pagination criteria.
     *         URL: http://localhost:8080/api/v1/recipe/page
     */
    @GetMapping("/page")
    public PaginationResponseDTO getRecipeListPage(@RequestParam(required = false) Long recipeSeqno,
            @RequestParam(required = false) String recipeCode,
            @RequestParam(required = false) String recipeName,
            @RequestParam(required = false) String typeName,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String sortDirection,
            @RequestParam(required = false) Long page,
            @RequestParam(required = false) Long size) {
        log.info("ENTRY", "getRecipeListPage");
        PaginationRequestDTO paginationRequestDTO = new PaginationRequestDTO(sort, sortDirection, page, size);
        RecipeSearchDTO search = new RecipeSearchDTO(recipeSeqno, recipeCode, recipeName, typeName);
        return recipeService.getRecipeListPage(search, paginationRequestDTO);
    }

    /**
     * Retrieves a list of RecipeType objects. If parameters are provided, the
     * results are filtered by the provided values.
     *
     * @param typeSeqno Optional sequence number of the recipe type to filter.
     * @param typeCode  Optional code of the recipe type to filter.
     * @param typeName  Optional name of the recipe type to filter.
     * @return A list of RecipeType objects that match the search criteria.
     *         URL: http://localhost:8080/api/v1/recipe/recipeType
     */
    @GetMapping("/recipeType")
    public List<RecipeType> getRecipeType(@RequestParam(required = false) Long typeSeqno,
            @RequestParam(required = false) String typeCode,
            @RequestParam(required = false) String typeName) {
        return recipeService.getRecipeTypes();
    }

    /**
     * Retrieves a list of RecipeDetailDTO objects. If recipeSeqno is provided, the
     * results are filtered by the provided recipe sequence number.
     *
     * @param recipeSeqno Optional sequence number of the recipe to filter.
     * @return A list of RecipeDetailDTO objects that match the search criteria.
     *         URL: http://localhost:8080/api/v1/recipe/recipeList
     */
    @GetMapping("/recipeList")
    public List<RecipeDetailDTO> getRecipe(@RequestParam(required = false) Long recipeSeqno) {
        return recipeService.getRecipes(recipeSeqno);
    }

    /**
     * Retrieves a RecipeDetailDTO object that matches the provided recipe sequence
     * number.
     *
     * @param recipeSeqno The sequence number of the recipe to retrieve.
     * @return A RecipeDetailDTO object that matches the search criteria.
     *         URL: http://localhost:8080/api/v1/recipe/recipeDetail/{recipeSeqno}
     */
    @GetMapping("/recipeDetail/{recipeSeqno}")
    public RecipeDetailDTO getRecipeDetail(@PathVariable Long recipeSeqno) {
        return recipeService.getRecipeDetails(recipeSeqno);
    }

    /**
     * Saves a new recipe or updates an existing one based on the provided recipe
     * request.
     *
     * @param recipeRequest The request containing details of the recipe to save or
     *                      update.
     *                      It includes fields such as recipeSeqno, recipeCode,
     *                      recipeName,
     *                      recipeDesc, recipeIngredient, recipeInstruction,
     *                      typeSeqno, and userId.
     *                      If recipeSeqno is provided, the existing recipe will be
     *                      updated.
     *                      Otherwise, a new recipe will be created.
     *                      URL: http://localhost:8080/api/v1/recipe/save
     */

    @PostMapping("/save")
    public void saveRecipe(@RequestBody RecipeRequest recipeRequest) {
        recipeService.saveRecipe(recipeRequest);
    }

    /**
     * Updates an existing recipe based on the provided recipe request.
     *
     * @param recipeRequest The request containing details of the recipe to update.
     *                      It includes fields such as recipeSeqno, recipeCode,
     *                      recipeName, recipeDesc, recipeIngredient,
     *                      recipeInstruction, typeSeqno, and userId.
     *                      If the recipeSeqno is provided, the existing recipe
     *                      with that sequence number will be updated.
     *                      URL: http://localhost:8080/api/v1/recipe/update
     */

    @PostMapping("/update")
    public void updateRecipe(@RequestBody RecipeRequest recipeRequest) {
        recipeService.saveRecipe(recipeRequest);
    }

    /**
     * Deletes a recipe based on the provided recipe sequence number and user ID.
     * If the user ID is not provided, the recipe will be deleted regardless of the
     * user ID.
     *
     * @param recipeSeqno The sequence number of the recipe to delete.
     * @param userId      The user ID of the user who is deleting the recipe.
     *                    If not provided, the recipe will be deleted regardless of
     *                    the user ID.
     *                    URL:
     *                    http://localhost:8080/api/v1/recipe/delete/{recipeSeqno}
     */
    @DeleteMapping("/delete/{recipeSeqno}")
    public void deleteRecipe(@PathVariable Long recipeSeqno, @RequestParam(required = false) Long userId) {
        recipeService.deleteRecipe(recipeSeqno, userId);
    }

    /**
     * Removes from table a recipe based on the provided recipe sequence number. If
     * the
     * sequence number is not provided, a 404 error will be returned. If the
     * sequence number is valid, the recipe will be deleted logically by setting
     * the active flag to 'I' (Inactive). This does not delete the recipe
     * physically from the database.
     *
     * @param recipeSeqno The sequence number of the recipe to delete.
     *                    URL:
     *                    http://localhost:8080/api/v1/recipe/remove/{recipeSeqno}
     */
    @DeleteMapping("/remove/{recipeSeqno}")
    public void removeRecipe(@PathVariable Long recipeSeqno) {
        recipeService.removeRecipe(recipeSeqno);
    }

    /**
     * Updates the image of a recipe based on the provided recipe sequence number.
     * If the image is not provided, a 400 error will be returned. If the
     * sequence number is valid, the recipe image will be updated with the
     * provided image. If the user ID is not provided, the image will be updated
     * regardless of the user ID.
     *
     * @param recipeSeqno The sequence number of the recipe to update the image.
     * @param file        The image to be updated.
     * @param userId      The user ID of the user who is updating the image. If not
     *                    provided, the image will be updated regardless of the user
     *                    ID.
     *                    URL:
     *                    http://localhost:8080/api/v1/recipe/saveImage/{recipeSeqno}
     * @return A ResponseEntity with a string body indicating the success or failure
     *         of the update operation.
     * @throws IOException If the image cannot be updated.
     */
    @PostMapping("/image/save/{recipeSeqno}")
    public ResponseEntity<String> updateImage(@PathVariable Long recipeSeqno,
            @RequestParam("image") MultipartFile file, @RequestParam(required = false) Long userId) {
        try {
            if (recipeSeqno == null) {
                return ResponseEntity.badRequest().body("Recipe not found");
            }
            recipeService.updateSaveImage(recipeSeqno, file, userId);
            return ResponseEntity.ok("Image updated successfully");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to update image: " + e.getMessage());
        }
    }

    /**
     * Retrieves the image of a recipe based on the provided recipe sequence number.
     *
     * @param recipeSeqno The sequence number of the recipe to retrieve the image.
     *                    URL:
     *                    http://localhost:8080/api/v1/recipe/getImage/{recipeSeqno}
     * @return A ResponseEntity containing the image as a byte array, and a content
     *         type of image/jpeg.
     */
    @GetMapping("/image/{recipeSeqno}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long recipeSeqno) {
        byte[] image = recipeService.getImageRecipe(recipeSeqno);
        if (image != null) {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
