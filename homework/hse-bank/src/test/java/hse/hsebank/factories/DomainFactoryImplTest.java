package hse.hsebank.factories;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class DomainFactoryImplTest {

    private final DomainFactoryImpl factory = new DomainFactoryImpl();

    @Test
    void testCreateBankAccount() {
        BankAccount account = factory.createBankAccount("Test Account");

        assertNotNull(account);
        assertNotNull(account.getId());
        assertEquals("Test Account", account.getName());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void testCreateCategory() {
        Category category = factory.createCategory(CategoryType.INCOME, "Salary");

        assertNotNull(category);
        assertNotNull(category.getId());
        assertEquals(CategoryType.INCOME, category.getType());
        assertEquals("Salary", category.getName());
    }

    @Test
    void testCreateOperation() {
        Operation operation = factory.createOperation(
                "acc123", "cat456", CategoryType.INCOME,
                new BigDecimal("100.00"), "Test operation"
        );

        assertNotNull(operation);
        assertNotNull(operation.getId());
        assertEquals("acc123", operation.getBankAccountId());
        assertEquals("cat456", operation.getCategoryId());
        assertEquals(CategoryType.INCOME, operation.getType());
        assertEquals(new BigDecimal("100.00"), operation.getAmount());
        assertEquals("Test operation", operation.getDescription());
        assertNotNull(operation.getDate());
    }

    @Test
    void testCreateOperationWithInvalidAmount() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.createOperation(
                        "acc123", "cat456", CategoryType.INCOME,
                        new BigDecimal("-50.00"), "Invalid operation"
                ));
    }
}