// CategoryType.java
package hse.hsebank.domains.enums;

import lombok.Getter;

/**
 * Operation category type
 */
@Getter
public enum CategoryType {
    INCOME("Income"),
    OUTCOME("Expense");

    private final String displayName;

    CategoryType(String displayName) {
        this.displayName = displayName;
    }

    public static CategoryType fromString(String type) {
        for (CategoryType categoryType : values()) {
            if (categoryType.name().equalsIgnoreCase(type) ||
                    categoryType.displayName.equalsIgnoreCase(type)) {
                return categoryType;
            }
        }
        throw new IllegalArgumentException("Unknown category type: " + type);
    }
}