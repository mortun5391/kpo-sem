package hse.hsebank.services;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.facade.BankAccountFacade;
import hse.hsebank.facade.CategoryFacade;
import hse.hsebank.facade.OperationFacade;
import hse.hsebank.factories.DomainFactoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class BalanceRecalculationServiceTest {

    private BalanceRecalculationService balanceService;
    private BankAccountFacade bankAccountFacade;
    private OperationFacade operationFacade;
    private CategoryFacade categoryFacade;

    @BeforeEach
    void setUp() {
        DomainFactoryImpl domainFactory = new DomainFactoryImpl();
        bankAccountFacade = new BankAccountFacade(domainFactory);
        categoryFacade = new CategoryFacade(domainFactory);
        operationFacade = new OperationFacade(domainFactory, bankAccountFacade, categoryFacade);
        balanceService = new BalanceRecalculationService(bankAccountFacade, operationFacade);
    }

    @Test
    void testRecalculateBalance() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        Category incomeCategory = categoryFacade.createCategory(CategoryType.INCOME, "Income");
        Category expenseCategory = categoryFacade.createCategory(CategoryType.OUTCOME, "Expense");

        account.processIncome(new BigDecimal("500.00"));

        operationFacade.createOperation(account.getId(), incomeCategory.getId(),
                CategoryType.INCOME, new BigDecimal("1000.00"), "Income 1");
        operationFacade.createOperation(account.getId(), expenseCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("400.00"), "Expense 1");

        balanceService.recalculateBalance(account.getId());

        BankAccount updatedAccount = bankAccountFacade.getAccount(account.getId()).get();
        assertEquals(new BigDecimal("600.00"), updatedAccount.getBalance());
    }

    @Test
    void testRecalculateAllBalances() {
        BankAccount account1 = bankAccountFacade.createAccount("Account 1");
        BankAccount account2 = bankAccountFacade.createAccount("Account 2");
        Category incomeCategory = categoryFacade.createCategory(CategoryType.INCOME, "Income");

        // Manually set incorrect balances
        account1.processIncome(new BigDecimal("100.00"));
        account2.processIncome(new BigDecimal("200.00"));

        operationFacade.createOperation(account1.getId(), incomeCategory.getId(),
                CategoryType.INCOME, new BigDecimal("500.00"), "Income");
        operationFacade.createOperation(account2.getId(), incomeCategory.getId(),
                CategoryType.INCOME, new BigDecimal("300.00"), "Income");

        balanceService.recalculateAllBalances();

        BankAccount updatedAccount1 = bankAccountFacade.getAccount(account1.getId()).get();
        BankAccount updatedAccount2 = bankAccountFacade.getAccount(account2.getId()).get();

        assertEquals(new BigDecimal("500.00"), updatedAccount1.getBalance());
        assertEquals(new BigDecimal("300.00"), updatedAccount2.getBalance());
    }

    @Test
    void testRecalculateNonExistentAccount() {
        assertThrows(IllegalArgumentException.class,
                () -> balanceService.recalculateBalance("nonexistent"));
    }
}