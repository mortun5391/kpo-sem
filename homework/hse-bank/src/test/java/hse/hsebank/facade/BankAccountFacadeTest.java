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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountFacadeTest {

    private BankAccountFacade bankAccountFacade;
    private DomainFactory domainFactory;

    @BeforeEach
    void setUp() {
        domainFactory = new DomainFactoryImpl();
        bankAccountFacade = new BankAccountFacade(domainFactory);
    }

    @Test
    void testCreateAccount() {
        BankAccount account = bankAccountFacade.createAccount("Test Account");

        assertNotNull(account);
        assertEquals("Test Account", account.getName());
        assertEquals(BigDecimal.ZERO, account.getBalance());

        Optional<BankAccount> retrieved = bankAccountFacade.getAccount(account.getId());
        assertTrue(retrieved.isPresent());
        assertEquals(account.getId(), retrieved.get().getId());
    }

    @Test
    void testGetAllAccounts() {
        bankAccountFacade.createAccount("Account 1");
        bankAccountFacade.createAccount("Account 2");

        assertEquals(2, bankAccountFacade.getAllAccounts().size());
    }

    @Test
    void testUpdateAccount() {
        BankAccount account = bankAccountFacade.createAccount("Old Name");

        boolean updated = bankAccountFacade.updateAccount(account.getId(), "New Name");

        assertTrue(updated);
        Optional<BankAccount> retrieved = bankAccountFacade.getAccount(account.getId());
        assertTrue(retrieved.isPresent());
        assertEquals("New Name", retrieved.get().getName());
    }

    @Test
    void testDeleteAccount() {
        BankAccount account = bankAccountFacade.createAccount("To Delete");

        boolean deleted = bankAccountFacade.deleteAccount(account.getId());

        assertTrue(deleted);
        assertFalse(bankAccountFacade.getAccount(account.getId()).isPresent());
    }
}