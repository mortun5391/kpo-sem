package hse.hsebank.template;

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
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class DataImporterTest {

    private BankAccountFacade bankAccountFacade;
    private CategoryFacade categoryFacade;
    private OperationFacade operationFacade;

    @BeforeEach
    void setUp() {
        DomainFactoryImpl domainFactory = new DomainFactoryImpl();
        bankAccountFacade = new BankAccountFacade(domainFactory);
        categoryFacade = new CategoryFacade(domainFactory);
        operationFacade = new OperationFacade(domainFactory, bankAccountFacade, categoryFacade);
    }

    @Test
    void testCsvDataImporter_ParseAccounts() {
        CsvDataImporter importer = new CsvDataImporter(bankAccountFacade, categoryFacade, operationFacade);

        String csvContent = "id,name,balance\nacc123,Test Account,1000.00\n";

        List<BankAccount> accounts = importer.parseAccounts(csvContent);

        assertEquals(1, accounts.size());
        assertEquals("acc123", accounts.getFirst().getId());
        assertEquals("Test Account", accounts.getFirst().getName());
        assertEquals(new BigDecimal("1000.00"), accounts.getFirst().getBalance());
    }

    @Test
    void testCsvDataImporter_ParseCategories() {
        CsvDataImporter importer = new CsvDataImporter(bankAccountFacade, categoryFacade, operationFacade);

        String csvContent = "id,type,name\ncat123,INCOME,Salary\n";

        List<Category> categories = importer.parseCategories(csvContent);

        assertEquals(1, categories.size());
        assertEquals("cat123", categories.get(0).getId());
        assertEquals(CategoryType.INCOME, categories.get(0).getType());
        assertEquals("Salary", categories.get(0).getName());
    }

    @Test
    void testCsvLineParsing() {
        CsvDataImporter importer = new CsvDataImporter(bankAccountFacade, categoryFacade, operationFacade);

        String[] result1 = importer.parseCsvLine("id,name,balance");
        assertEquals(3, result1.length);

        String[] result2 = importer.parseCsvLine("\"id\",\"name,with,commas\",\"balance\"");
        assertEquals(3, result2.length);
        assertEquals("name,with,commas", result2[1]);
    }
}