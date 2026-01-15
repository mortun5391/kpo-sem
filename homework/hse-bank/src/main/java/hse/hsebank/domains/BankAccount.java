package hse.hsebank.domains;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Bank account
 */
@ToString
public class BankAccount {
    @Getter
    private final String id;

    @Getter @Setter
    private String name;

    @Getter
    private BigDecimal balance;

    @JsonCreator
    public BankAccount(@JsonProperty("id") String id,
                       @JsonProperty("name") String name,
                       @JsonProperty("balance") BigDecimal balance) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Account name cannot be empty");
        }
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be empty");
        }

        this.name = name.trim();
        this.id = id.trim();
        this.balance = balance != null ? balance : BigDecimal.ZERO;
    }

    public static BankAccount createNew(String name) {
        String shortId = hse.hsebank.utils.ShortUUID.generate();
        return new BankAccount(shortId, name, BigDecimal.ZERO);
    }

    /**
     * Process income operation
     */
    public void processIncome(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Income amount must be positive");
        }
        this.balance = this.balance.add(amount);
    }

    /**
     * Process expense operation
     */
    public void processExpense(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Expense amount must be positive");
        }
        if (this.balance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds. Current balance: " + balance);
        }
        this.balance = this.balance.subtract(amount);
    }

    /**
     * Process operation based on its type
     */
    public void processOperation(Operation operation) {
        if (operation.isIncome()) {
            processIncome(operation.getAmount());
        } else if (operation.isExpense()) {
            processExpense(operation.getAmount());
        }
    }

    /**
     * Recalculate balance based on operations
     */
    public void recalculateBalance(BigDecimal newBalance) {
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = newBalance;
    }
}