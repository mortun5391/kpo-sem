package hse.hsebank.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import hse.hsebank.domains.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Operation category
 */
@ToString
public class Category {
    @Getter
    private final String id;

    @Getter @Setter
    private CategoryType type;

    @Getter @Setter
    private String name;

    @JsonCreator
    public Category(@JsonProperty("id") String id,
                    @JsonProperty("type") CategoryType type,
                    @JsonProperty("name") String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Category type cannot be null");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        this.id = id.trim();
        this.type = type;
        this.name = name.trim();
    }

    /**
     * Check if category is for income operations
     */
    public boolean isIncome() {
        return type == CategoryType.INCOME;
    }

    /**
     * Check if category is for expense operations
     */
    public boolean isExpense() {
        return type == CategoryType.OUTCOME;
    }
}