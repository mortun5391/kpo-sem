package hse.hsebank.domains;

import hse.hsebank.domains.enums.CategoryType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void testCreateNewAccount() {
        BankAccount account = BankAccount.createNew("Test Account");

        assertNotNull(account.getId());
        assertEquals(8, account.getId().length());
        assertEquals("Test Account", account.getName());
        assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test
    void testProcessIncome() {
        BankAccount account = BankAccount.createNew("Test Account");
        BigDecimal initialBalance = account.getBalance();
        BigDecimal incomeAmount = new BigDecimal("100.50");

        account.processIncome(incomeAmount);

        assertEquals(initialBalance.add(incomeAmount), account.getBalance());
    }

    @Test
    void testProcessExpense() {
        BankAccount account = BankAccount.createNew("Test Account");
        account.processIncome(new BigDecimal("200.00"));

        account.processExpense(new BigDecimal("50.25"));

        assertEquals(new BigDecimal("149.75"), account.getBalance());
    }

    @Test
    void testProcessExpenseWithInsufficientFunds() {
        BankAccount account = BankAccount.createNew("Test Account");
        account.processIncome(new BigDecimal("50.00"));

        assertThrows(IllegalArgumentException.class,
                () -> account.processExpense(new BigDecimal("100.00")));
    }

    @Test
    void testProcessOperationIncome() {
        BankAccount account = BankAccount.createNew("Test Account");
        Operation operation = Operation.createNew(
                account.getId(), "cat123", CategoryType.INCOME,
                new BigDecimal("75.00"), "Test income"
        );

        account.processOperation(operation);

        assertEquals(new BigDecimal("75.00"), account.getBalance());
    }

    @Test
    void testProcessOperationExpense() {
        BankAccount account = BankAccount.createNew("Test Account");
        account.processIncome(new BigDecimal("100.00"));
        Operation operation = Operation.createNew(
                account.getId(), "cat123", CategoryType.OUTCOME,
                new BigDecimal("25.00"), "Test expense"
        );

        account.processOperation(operation);

        assertEquals(new BigDecimal("75.00"), account.getBalance());
    }
}