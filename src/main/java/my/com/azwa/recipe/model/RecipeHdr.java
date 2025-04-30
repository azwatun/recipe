package my.com.azwa.recipe.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ph_recipe_hdr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeHdr {

    @Id
    @SequenceGenerator(name = "recipe_seqno", sequenceName = "ph_recipe_hdr_seq", allocationSize = 1)
    @GeneratedValue(generator = "recipe_seqno")
    private Long recipeSeqno;

    @Column(name = "recipe_code")
    private String recipeCode;

    @Column(name = "recipe_name")
    private String recipeName;

    @Column(name = "recipe_desc")
    private String recipeDesc;

    @Column(name = "recipe_ingredient")
    private String recipeIngredient;

    @Column(name = "recipe_instruction")
    private String recipeInstruction;

    @Column(name = "image")
    private byte[] image;

    @Column(name = "active_flag")
    private String activeFlag;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_date", length = 29)
    private LocalDateTime createdDate;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_date", length = 29)
    private LocalDateTime updatedDate;

    @Column(name = "type_seqno")
    private Long typeSeqno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_seqno", insertable = false, updatable = false)
    public RecipeType recipeType;
}
