package hse.hsebank.proxy;

import hse.hsebank.domains.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class BankRepositoryProxyTest {

    private BankRepositoryImpl realRepository;
    private BankRepositoryProxy proxyRepository;

    @BeforeEach
    void setUp() {
        realRepository = new BankRepositoryImpl();
        proxyRepository = new BankRepositoryProxy(realRepository);
    }

    @Test
    void testProxyCaching() {
        BankAccount account = new BankAccount("test123", "Test Account", new BigDecimal("1000.00"));

        proxyRepository.save(account);

        Optional<BankAccount> result1 = proxyRepository.findById("test123");
        assertTrue(result1.isPresent());

        Optional<BankAccount> result2 = proxyRepository.findById("test123");
        assertTrue(result2.isPresent());

        assertEquals(result1.get(), result2.get());
    }

    @Test
    void testProxyCacheInvalidationOnDelete() {
        BankAccount account = new BankAccount("test123", "Test Account", new BigDecimal("1000.00"));

        proxyRepository.save(account);
        proxyRepository.findById("test123");

        proxyRepository.delete("test123");

        Optional<BankAccount> result = proxyRepository.findById("test123");
        assertFalse(result.isPresent());
    }

    @Test
    void testClearCache() {
        BankAccount account = new BankAccount("test123", "Test Account", new BigDecimal("1000.00"));

        proxyRepository.save(account);
        proxyRepository.findById("test123"); // Put in cache

        proxyRepository.clearCache();

        Optional<BankAccount> result = proxyRepository.findById("test123");
        assertTrue(result.isPresent());
    }
}