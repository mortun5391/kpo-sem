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
import java.time.LocalDateTime;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class AnalyticsFacadeTest {

    private AnalyticsFacade analyticsFacade;
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
        analyticsFacade = new AnalyticsFacade(operationFacade, categoryFacade);
    }

    @Test
    void testGetTotalIncomeAndExpenses() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        Category incomeCategory = categoryFacade.createCategory(CategoryType.INCOME, "Salary");
        Category expenseCategory = categoryFacade.createCategory(CategoryType.OUTCOME, "Food");

        operationFacade.createOperation(account.getId(), incomeCategory.getId(),
                CategoryType.INCOME, new BigDecimal("1000.00"), "Salary");
        operationFacade.createOperation(account.getId(), expenseCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("200.00"), "Groceries");
        operationFacade.createOperation(account.getId(), expenseCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("50.00"), "Lunch");

        BigDecimal totalIncome = analyticsFacade.getTotalIncome();
        BigDecimal totalExpenses = analyticsFacade.getTotalExpenses();

        assertEquals(new BigDecimal("1000.00"), totalIncome);
        assertEquals(new BigDecimal("250.00"), totalExpenses);
    }

    @Test
    void testGetIncomeByCategory() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        Category salaryCategory = categoryFacade.createCategory(CategoryType.INCOME, "Salary");
        Category bonusCategory = categoryFacade.createCategory(CategoryType.INCOME, "Bonus");

        operationFacade.createOperation(account.getId(), salaryCategory.getId(),
                CategoryType.INCOME, new BigDecimal("1000.00"), "Salary");
        operationFacade.createOperation(account.getId(), bonusCategory.getId(),
                CategoryType.INCOME, new BigDecimal("500.00"), "Bonus");
        operationFacade.createOperation(account.getId(), salaryCategory.getId(),
                CategoryType.INCOME, new BigDecimal("1000.00"), "Salary");

        Map<String, BigDecimal> incomeByCategory = analyticsFacade.getIncomeByCategory();

        assertEquals(2, incomeByCategory.size());
        assertEquals(new BigDecimal("2000.00"), incomeByCategory.get("Salary"));
        assertEquals(new BigDecimal("500.00"), incomeByCategory.get("Bonus"));
    }

    @Test
    void testGetFinancialSummary() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");
        Category incomeCategory = categoryFacade.createCategory(CategoryType.INCOME, "Income");
        Category expenseCategory = categoryFacade.createCategory(CategoryType.OUTCOME, "Expense");

        operationFacade.createOperation(account.getId(), incomeCategory.getId(),
                CategoryType.INCOME, new BigDecimal("1500.00"), "Income");
        operationFacade.createOperation(account.getId(), expenseCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("500.00"), "Expense");

        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);

        AnalyticsFacade.FinancialSummary summary = analyticsFacade.getFinancialSummary(start, end);

        assertEquals(new BigDecimal("1500.00"), summary.getTotalIncome());
        assertEquals(new BigDecimal("500.00"), summary.getTotalExpenses());
        assertEquals(new BigDecimal("1000.00"), summary.getNetBalance());
    }

    @Test
    void testGetTopCategories() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");

        Category incomeCategory = categoryFacade.createCategory(CategoryType.INCOME, "Income");
        operationFacade.createOperation(account.getId(), incomeCategory.getId(),
                CategoryType.INCOME, new BigDecimal("2000.00"), "Initial deposit");

        Category foodCategory = categoryFacade.createCategory(CategoryType.OUTCOME, "Food");
        Category rentCategory = categoryFacade.createCategory(CategoryType.OUTCOME, "Rent");
        Category transportCategory = categoryFacade.createCategory(CategoryType.OUTCOME, "Transport");

        operationFacade.createOperation(account.getId(), rentCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("1000.00"), "Rent");
        operationFacade.createOperation(account.getId(), foodCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("300.00"), "Food");
        operationFacade.createOperation(account.getId(), foodCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("200.00"), "Food");
        operationFacade.createOperation(account.getId(), transportCategory.getId(),
                CategoryType.OUTCOME, new BigDecimal("100.00"), "Transport");

        Map<String, BigDecimal> topExpenses = analyticsFacade.getTopSpendingCategories(2);

        assertEquals(2, topExpenses.size());
        assertTrue(topExpenses.containsKey("Rent"));
        assertTrue(topExpenses.containsKey("Food"));
        assertEquals(new BigDecimal("1000.00"), topExpenses.get("Rent"));
        assertEquals(new BigDecimal("500.00"), topExpenses.get("Food"));
    }
}