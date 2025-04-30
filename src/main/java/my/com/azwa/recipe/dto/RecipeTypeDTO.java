package my.com.azwa.recipe.dto;

import java.time.LocalDateTime;

public record RecipeTypeDTO(
                Long typeSeqno,
                String typeCode,
                String typeName,
                String activeFlag,
                LocalDateTime createdDate,
                LocalDateTime updatedDate) {

}
