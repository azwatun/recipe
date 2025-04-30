package my.com.azwa.recipe.repository;

import static org.jooq.impl.DSL.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.SelectConditionStep;
import org.jooq.SelectLimitPercentAfterOffsetStep;
import org.jooq.SortField;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import my.com.azwa.recipe.dto.PaginationRequestDTO;
import my.com.azwa.recipe.dto.RecipeListDTO;
import my.com.azwa.recipe.dto.RecipeSearchDTO;

@Repository
@Slf4j
public class RecipeRepositoryJooq {
    DSLContext dsl;

    public RecipeRepositoryJooq(DSLContext dsl) {
        this.dsl = dsl;
    }

    public List<RecipeListDTO> getRecipeList(RecipeSearchDTO search, PaginationRequestDTO paginationRequestDTO) {
        log.info("REPO", "getRequesterUnitByUserId");

        Condition cond = noCondition();
        cond = cond.and(field("rh.active_flag").eq("A"));
        if (search.recipeSeqno() != null) {
            cond = cond.and(field("rh.recipe_seqno").eq(search.recipeSeqno()));
        }
        if (StringUtils.isNotBlank(search.recipeCode())) {
            cond = cond.and(field("rh.recipe_code").containsIgnoreCase(search.recipeCode()));
        }
        if (StringUtils.isNotBlank(search.recipeName())) {
            cond = cond.and(field("rh.recipe_name").containsIgnoreCase(search.recipeName()));
        }
        if (StringUtils.isNotBlank(search.recipeCode())) {
            cond = cond.and(field("rt.type_name").containsIgnoreCase(search.typeName()));
        }
        SelectLimitPercentAfterOffsetStep<Record5<Long, String, String, String, String>> query = dsl.select(
                field("rh.recipe_seqno", Long.class),
                field("rh.recipe_code", String.class).as("recipeCode"),
                field("rh.recipe_name", String.class).as("recipeName"),
                field("rt.type_name", String.class).as("typeName"),
                field("rh.recipe_desc", String.class))
                .from("ph_recipe_hdr rh")
                .leftJoin("ph_recipe_type rt")
                .on(field("rh.type_seqno").eq(field("rt.type_seqno")))
                .where(cond)
                .orderBy(
                        getOrderByField(
                                paginationRequestDTO.sort(), paginationRequestDTO.sortDirection()))
                .offset((paginationRequestDTO.page() - 1) * paginationRequestDTO.size())
                .limit(paginationRequestDTO.size());
        log.info("QUERY->" + query.toString());
        return query.fetchInto(RecipeListDTO.class);
    }

    public static SortField<Object> getOrderByField(String sort, String sortDirection) {
        return sortDirection.equals("asc") ? field(name(sort)).asc() : field(name(sort)).desc();
    }

    public Long getRecipeListPage(RecipeSearchDTO search) {
        log.info("REPO", "getRequesterUnitByUserId");

        Condition cond = noCondition();
        cond = cond.and(field("rh.active_flag").eq("A"));
        if (search.recipeSeqno() != null) {
            cond = cond.and(field("rh.recipe_seqno").eq(search.recipeSeqno()));
        }
        if (StringUtils.isNotBlank(search.recipeCode())) {
            cond = cond.and(field("rh.recipe_code").containsIgnoreCase(search.recipeCode()));
        }
        if (StringUtils.isNotBlank(search.recipeName())) {
            cond = cond.and(field("rh.recipe_name").containsIgnoreCase(search.recipeName()));
        }
        if (StringUtils.isNotBlank(search.typeName())) {
            cond = cond.and(field("rt.type_name").containsIgnoreCase(search.typeName()));
        }
        SelectConditionStep<Record1<Integer>> query = dsl.selectCount()
                .from("ph_recipe_hdr rh")
                .leftJoin("ph_recipe_type rt")
                .on(field("rh.type_seqno").eq(field("rt.type_seqno")))
                .where(cond);
        log.info("QUERY->" + query.toString());
        return query.fetchOneInto(Long.class);
    }
}
