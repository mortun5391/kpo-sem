package hse.hsebank.visitor;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataExportVisitorTest {

    @Test
    void testCsvExportVisitor() {
        CsvExportVisitor visitor = new CsvExportVisitor();

        List<BankAccount> accounts = List.of(
                new BankAccount("acc1", "Main Account", new BigDecimal("1000.00"))
        );

        List<Category> categories = List.of(
                new Category("cat1", CategoryType.INCOME, "Salary")
        );

        List<Operation> operations = List.of(
                new Operation("op1", "acc1", "cat1", CategoryType.INCOME,
                        new BigDecimal("1000.00"), LocalDateTime.now(), "Monthly salary")
        );

        String result = visitor.exportAll(accounts, categories, operations);

        assertNotNull(result);
        assertTrue(result.contains("Main Account"));
        assertTrue(result.contains("Salary"));
        assertTrue(result.contains("Monthly salary"));
        assertTrue(result.contains("=== ACCOUNTS ==="));
        assertTrue(result.contains("=== CATEGORIES ==="));
        assertTrue(result.contains("=== OPERATIONS ==="));
    }

    @Test
    void testJsonExportVisitor() {
        JsonExportVisitor visitor = new JsonExportVisitor();

        List<BankAccount> accounts = List.of(
                new BankAccount("acc1", "Test Account", new BigDecimal("500.00"))
        );

        List<Category> categories = List.of(
                new Category("cat1", CategoryType.OUTCOME, "Food")
        );

        String result = visitor.exportAccounts(accounts);

        assertNotNull(result);
        assertTrue(result.contains("Test Account"));
        assertTrue(result.contains("500.00"));
    }

    @Test
    void testYamlExportVisitor() {
        YamlExportVisitor visitor = new YamlExportVisitor();

        List<Operation> operations = List.of(
                new Operation("op1", "acc1", "cat1", CategoryType.INCOME,
                        new BigDecimal("1500.00"), LocalDateTime.now(), "Bonus")
        );

        String result = visitor.exportOperations(operations);

        assertNotNull(result);
        assertTrue(result.contains("op1"));
        assertTrue(result.contains("Bonus"));
        assertTrue(result.contains("1500.00"));
    }
}