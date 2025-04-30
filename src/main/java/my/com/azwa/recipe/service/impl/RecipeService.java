package my.com.azwa.recipe.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.com.azwa.recipe.dto.PaginationRequestDTO;
import my.com.azwa.recipe.dto.PaginationResponseDTO;
import my.com.azwa.recipe.dto.RecipeDetailDTO;
import my.com.azwa.recipe.dto.RecipeListDTO;
import my.com.azwa.recipe.dto.RecipeRequest;
import my.com.azwa.recipe.dto.RecipeSearchDTO;
import my.com.azwa.recipe.exception.PhisException;
import my.com.azwa.recipe.mapper.RecipeListMapper;
import my.com.azwa.recipe.model.RecipeHdr;
import my.com.azwa.recipe.model.RecipeType;
import my.com.azwa.recipe.repository.RecipeHdrRepository;
import my.com.azwa.recipe.repository.RecipeRepositoryJooq;
import my.com.azwa.recipe.repository.RecipeTypeRepository;
import my.com.azwa.recipe.service.IRecipeService;
import my.com.azwa.recipe.util.PaginationUtil;

@Service
@Slf4j
@AllArgsConstructor
public class RecipeService implements IRecipeService {

    private final RecipeHdrRepository recipeHdrRepository;
    private final RecipeTypeRepository recipeTypeRepository;
    private final RecipeRepositoryJooq recipeRepositoryJooq;

    public List<RecipeType> getRecipeTypes() {
        return recipeTypeRepository.findAll();
    }

    public List<RecipeDetailDTO> getRecipes(Long recipeSeqno) {
        List<RecipeDetailDTO> recipeDetailDTOs = new ArrayList<>();
        List<RecipeHdr> recipeHdrs = null;
        if (recipeSeqno != null) {
            recipeHdrs = new ArrayList<>();
            RecipeHdr recipeHdr = recipeHdrRepository.getReferenceById(recipeSeqno);
            if (recipeHdr != null) {
                recipeHdrs.add(recipeHdr);
            }
        } else {
            recipeHdrs = recipeHdrRepository.findAll();
        }
        recipeHdrs.forEach(recipeHdr -> {
            RecipeDetailDTO recipeDetailDTO = new RecipeDetailDTO(
                    recipeHdr.getRecipeSeqno(),
                    recipeHdr.getRecipeCode(),
                    recipeHdr.getRecipeName(),
                    recipeHdr.getRecipeDesc(),
                    recipeHdr.getRecipeIngredient(),
                    recipeHdr.getRecipeInstruction(),
                    recipeHdr.getRecipeType().getTypeCode(),
                    recipeHdr.getRecipeType().getTypeName(),
                    recipeHdr.getCreatedDate(),
                    recipeHdr.getUpdatedDate());
            recipeDetailDTOs.add(recipeDetailDTO);
        });
        return recipeDetailDTOs;
    }

    public RecipeDetailDTO getRecipeDetails(Long recipeSeqno) {
        if (recipeSeqno != null) {
            RecipeHdr recipeHdr = recipeHdrRepository.getReferenceById(recipeSeqno);
            if (recipeHdr != null) {
                RecipeDetailDTO recipeDetailDTO = new RecipeDetailDTO(
                        recipeHdr.getRecipeSeqno(),
                        recipeHdr.getRecipeCode(),
                        recipeHdr.getRecipeName(),
                        recipeHdr.getRecipeDesc(),
                        recipeHdr.getRecipeIngredient(),
                        recipeHdr.getRecipeInstruction(),
                        recipeHdr.getRecipeType().getTypeCode(),
                        recipeHdr.getRecipeType().getTypeName(),
                        recipeHdr.getCreatedDate(),
                        recipeHdr.getUpdatedDate());
                return recipeDetailDTO;
            } else {
                throw new PhisException("GET RECIPE", null, "Recipe not found");
            }
        } else {
            throw new PhisException("GET RECIPE", null, "Recipe not found");
        }
    }

    public List<RecipeListDTO> getRecipeList(RecipeSearchDTO search, PaginationRequestDTO paginationRequestDTO) {
        log.info("SERVICE", "getRecipeList");
        paginationRequestDTO = PaginationUtil.pageSorting(paginationRequestDTO, new RecipeListMapper(), false);
        return recipeRepositoryJooq.getRecipeList(search, paginationRequestDTO);
    }

    public PaginationResponseDTO getRecipeListPage(RecipeSearchDTO search, PaginationRequestDTO paginationRequestDTO) {
        log.info("SERVICE", "getRecipeListPage");
        paginationRequestDTO = PaginationUtil.pageSorting(paginationRequestDTO, new RecipeListMapper(), false);
        Long total = recipeRepositoryJooq.getRecipeListPage(search);
        return PaginationUtil.pagination(paginationRequestDTO.size(), total);
    }

    public byte[] getImageRecipe(Long seqno) {
        if (seqno == null) {
            throw new PhisException("GET IMAGE", null, "Recipe not found");
        }
        RecipeHdr recipeHdr = recipeHdrRepository.findByRecipeSeqno(seqno);
        if (recipeHdr != null) {

            if (recipeHdr.getImage() != null)
                return recipeHdr.getImage();
            else
                throw new PhisException("GET IMAGE", null, "Recipe not found");
        } else {
            throw new PhisException("GET IMAGE", null, "Recipe not found");
        }
    }

    public void updateSaveImage(Long recipeSeqno, MultipartFile file, Long userId) {
        if (recipeSeqno != null) {
            try {
                RecipeHdr recipeHdr = recipeHdrRepository.getReferenceById(recipeSeqno);
                if (recipeHdr != null) {
                    recipeHdr.setImage(file.getBytes());
                    recipeHdr.setUpdatedDate(LocalDateTime.now());
                    recipeHdr.setUpdatedBy(userId);
                    recipeHdrRepository.save(recipeHdr);
                }
            } catch (Exception e) {
                throw new PhisException("SAVE/UPDATE IMAGE", e, null);
            }
        } else {
            throw new PhisException("SAVE/UPDATE IMAGE", null, "Recipe not found");
        }
    }

    public void saveRecipe(RecipeRequest recipeRequest) {
        Boolean isExist = false;
        RecipeHdr recipeHdr = null;
        if (recipeRequest != null && recipeRequest.recipeSeqno() != null) {
            recipeHdr = recipeHdrRepository.getReferenceById(recipeRequest.recipeSeqno());
            if (recipeHdr != null)
                isExist = true;
        }
        if (!isExist) {
            recipeHdr = new RecipeHdr();
            recipeHdr.setCreatedBy(recipeRequest.userId());
            recipeHdr.setCreatedDate(LocalDateTime.now());
            recipeHdr.setActiveFlag("A");
        }
        if (recipeHdr != null) {
            recipeHdr.setRecipeCode(recipeRequest.recipeCode());
            recipeHdr.setRecipeName(recipeRequest.recipeName());
            recipeHdr.setRecipeDesc(recipeRequest.recipeDesc());
            recipeHdr.setRecipeIngredient(recipeRequest.recipeIngredient());
            recipeHdr.setRecipeInstruction(recipeRequest.recipeInstruction());
            recipeHdr.setTypeSeqno(recipeRequest.typeSeqno());
            recipeHdr.setUpdatedBy(recipeRequest.userId());
            recipeHdr.setUpdatedDate(LocalDateTime.now());
            recipeHdrRepository.save(recipeHdr);
        }
    }

    public void removeRecipe(Long recipeSeqno) {
        if (recipeSeqno != null) {
            recipeHdrRepository.deleteById(recipeSeqno);
        } else {
            throw new PhisException("REMOVE IMAGE", null, "Recipe not found");
        }
    }

    public void deleteRecipe(Long recipeSeqno, Long userId) {
        if (recipeSeqno != null) {
            RecipeHdr recipeHdr = recipeHdrRepository.getReferenceById(recipeSeqno);
            if (recipeHdr != null) {
                recipeHdr.setActiveFlag("I");
                recipeHdr.setUpdatedBy(userId);
                recipeHdr.setUpdatedDate(LocalDateTime.now());
                recipeHdrRepository.save(recipeHdr);
            }
        } else {
            throw new PhisException("DELETE IMAGE", null, "Recipe not found");
        }
    }

}
