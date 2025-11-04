package hse.hsebank.domains;

import hse.hsebank.domains.enums.CategoryType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class OperationTest {

    @Test
    void testCreateNewOperation() {
        Operation operation = Operation.createNew(
                "acc123", "cat456", CategoryType.INCOME,
                new BigDecimal("100.00"), "Test operation"
        );

        assertNotNull(operation.getId());
        assertEquals(8, operation.getId().length());
        assertEquals("acc123", operation.getBankAccountId());
        assertEquals("cat456", operation.getCategoryId());
        assertEquals(CategoryType.INCOME, operation.getType());
        assertEquals(new BigDecimal("100.00"), operation.getAmount());
        assertEquals("Test operation", operation.getDescription());
        assertNotNull(operation.getDate());
    }

    @Test
    void testIsIncome() {
        Operation incomeOp = Operation.createNew(
                "acc123", "cat456", CategoryType.INCOME,
                new BigDecimal("100.00"), "Income"
        );
        Operation expenseOp = Operation.createNew(
                "acc123", "cat456", CategoryType.OUTCOME,
                new BigDecimal("50.00"), "Expense"
        );

        assertTrue(incomeOp.isIncome());
        assertFalse(expenseOp.isIncome());
    }

    @Test
    void testIsExpense() {
        Operation incomeOp = Operation.createNew(
                "acc123", "cat456", CategoryType.INCOME,
                new BigDecimal("100.00"), "Income"
        );
        Operation expenseOp = Operation.createNew(
                "acc123", "cat456", CategoryType.OUTCOME,
                new BigDecimal("50.00"), "Expense"
        );

        assertFalse(incomeOp.isExpense());
        assertTrue(expenseOp.isExpense());
    }

    @Test
    void testInvalidAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.createNew("acc123", "cat456", CategoryType.INCOME,
                        new BigDecimal("-50.00"), "Invalid amount"));
    }
}