package my.com.azwa.recipe.dto;

public record PaginationRequestDTO(String sort, String sortDirection, Long page, Long size) {
}
