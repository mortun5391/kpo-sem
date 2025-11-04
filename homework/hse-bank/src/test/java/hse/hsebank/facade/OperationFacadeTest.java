package hse.hsebank.facade;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.factories.DomainFactory;
import hse.hsebank.factories.DomainFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OperationFacadeTest {

    private OperationFacade operationFacade;
    private BankAccountFacade bankAccountFacade;
    private CategoryFacade categoryFacade;
    private DomainFactory domainFactory;

    @BeforeEach
    void setUp() {
        domainFactory = new DomainFactoryImpl();
        bankAccountFacade = new BankAccountFacade(domainFactory);
        categoryFacade = new CategoryFacade(domainFactory);
        operationFacade = new OperationFacade(domainFactory, bankAccountFacade, categoryFacade);
    }

    @Test
    void testCreateOperation() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        Category category = categoryFacade.createCategory(CategoryType.INCOME, "Salary");

        Operation operation = operationFacade.createOperation(
                account.getId(), category.getId(), CategoryType.INCOME,
                new BigDecimal("1000.00"), "Monthly salary"
        );

        assertNotNull(operation);
        assertEquals(account.getId(), operation.getBankAccountId());
        assertEquals(category.getId(), operation.getCategoryId());
        assertEquals(CategoryType.INCOME, operation.getType());
        assertEquals(new BigDecimal("1000.00"), operation.getAmount());

        Optional<BankAccount> updatedAccount = bankAccountFacade.getAccount(account.getId());
        assertTrue(updatedAccount.isPresent());
        assertEquals(new BigDecimal("1000.00"), updatedAccount.get().getBalance());
    }

    @Test
    void testCreateExpenseOperation() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        account.processIncome(new BigDecimal("500.00"));
        Category category = categoryFacade.createCategory(CategoryType.OUTCOME, "Food");

        Operation operation = operationFacade.createOperation(
                account.getId(), category.getId(), CategoryType.OUTCOME,
                new BigDecimal("50.00"), "Groceries"
        );

        assertNotNull(operation);
        assertEquals(CategoryType.OUTCOME, operation.getType());

        Optional<BankAccount> updatedAccount = bankAccountFacade.getAccount(account.getId());
        assertTrue(updatedAccount.isPresent());
        assertEquals(new BigDecimal("450.00"), updatedAccount.get().getBalance());
    }

    @Test
    void testCreateOperationWithInsufficientFunds() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        Category category = categoryFacade.createCategory(CategoryType.OUTCOME, "Expensive");

        assertThrows(IllegalArgumentException.class,
                () -> operationFacade.createOperation(
                        account.getId(), category.getId(), CategoryType.OUTCOME,
                        new BigDecimal("100.00"), "Too expensive"
                ));
    }

    @Test
    void testGetOperationsByAccount() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        Category category = categoryFacade.createCategory(CategoryType.INCOME, "Income");

        operationFacade.createOperation(account.getId(), category.getId(),
                CategoryType.INCOME, new BigDecimal("100.00"), "Op1");
        operationFacade.createOperation(account.getId(), category.getId(),
                CategoryType.INCOME, new BigDecimal("200.00"), "Op2");

        List<Operation> operations = operationFacade.getOperationsByAccount(account.getId());
        assertEquals(2, operations.size());
    }
}