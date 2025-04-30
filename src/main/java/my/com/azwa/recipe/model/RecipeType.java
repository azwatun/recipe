package my.com.azwa.recipe.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ph_recipe_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeType {
    @Id
    @SequenceGenerator(name = "type_seqno", sequenceName = "ph_recipe_type_seq", allocationSize = 1)
    @GeneratedValue(generator = "type_seqno")
    private Long typeSeqno;

    @Column(name = "type_code")
    private String typeCode;

    @Column(name = "type_name")
    private String typeName;

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
}
