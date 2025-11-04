package hse.hsebank.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import hse.hsebank.domains.enums.CategoryType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Financial operation (income or expense)
 */
public class Operation {
    @Getter
    private final String id;

    @Getter @Setter
    private String bankAccountId;

    @Getter @Setter
    private String categoryId;

    @Getter
    private CategoryType type;

    @Getter @Setter
    private BigDecimal amount;

    @Getter
    private final LocalDateTime date;

    @Getter @Setter
    private String description;

    // Единственный конструктор с аннотациями Jackson
    @JsonCreator
    public Operation(@JsonProperty("id") String id,
                     @JsonProperty("bankAccountId") String bankAccountId,
                     @JsonProperty("categoryId") String categoryId,
                     @JsonProperty("type") CategoryType type,
                     @JsonProperty("amount") BigDecimal amount,
                     @JsonProperty("date") LocalDateTime date,
                     @JsonProperty("description") String description) {
        validateInput(id, bankAccountId, categoryId, type, amount);

        this.id = id;
        this.bankAccountId = bankAccountId;
        this.categoryId = categoryId;
        this.type = type;
        this.amount = amount;
        this.date = date != null ? date : LocalDateTime.now();
        this.description = description != null ? description.trim() : "";
    }

    public static Operation createNew(String bankAccountId, String categoryId, CategoryType type,
                                      BigDecimal amount, String description) {
        String shortId = hse.hsebank.utils.ShortUUID.generate();
        return new Operation(shortId, bankAccountId, categoryId, type, amount, LocalDateTime.now(), description);
    }

    private void validateInput(String id, String bankAccountId, String categoryId,
                               CategoryType type, BigDecimal amount) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Operation ID cannot be null");
        }
        if (bankAccountId == null || bankAccountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Bank account ID cannot be null");
        }
        if (categoryId == null || categoryId.trim().isEmpty()) {
            throw new IllegalArgumentException("Category ID cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Operation type cannot be null");
        }
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    /**
     * Set operation type with validation
     */
    public void setType(CategoryType type) {
        if (type == null) {
            throw new IllegalArgumentException("Operation type cannot be null");
        }
        this.type = type;
    }

    /**
     * Check if operation is income
     */
    public boolean isIncome() {
        return type == CategoryType.INCOME;
    }

    /**
     * Check if operation is expense
     */
    public boolean isExpense() {
        return type == CategoryType.OUTCOME;
    }

    /**
     * Update operation date (for import/export scenarios)
     */
    public void setDate(LocalDateTime date) {
        if (date == null) {
            throw new IllegalArgumentException("Operation date cannot be null");
        }
        if (date.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Operation date cannot be in the future");
        }
    }

    /**
     * Get formatted date string
     */
    public String getFormattedDate() {
        return date.toString();
    }

    @Override
    public String toString() {
        return String.format("Operation{id=%s, accountId=%s, categoryId=%s, type=%s, amount=%.2f, date=%s, description='%s'}",
                id, bankAccountId, categoryId, type, amount, date, description);
    }
}