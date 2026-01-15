package hse.hsebank.proxy;

import hse.hsebank.domains.BankAccount;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Proxy with in-memory cache
 */
@Component
public class BankRepositoryProxy implements BankRepository {
    private final BankRepository realRepository;
    private final ConcurrentMap<String, BankAccount> cache = new ConcurrentHashMap<>();

    public BankRepositoryProxy(BankRepositoryImpl realRepository) {
        this.realRepository = realRepository;
    }

    @Override
    public void save(BankAccount account) {
        realRepository.save(account);
        cache.put(account.getId(), account);
    }

    @Override
    public Optional<BankAccount> findById(String id) {
        BankAccount cached = cache.get(id);
        if (cached != null) {
            System.out.println("Returning from cache: " + id);
            return Optional.of(cached);
        }

        Optional<BankAccount> account = realRepository.findById(id);
        account.ifPresent(acc -> cache.put(id, acc));
        return account;
    }

    @Override
    public List<BankAccount> findAll() {
        return realRepository.findAll();
    }

    @Override
    public void delete(String id) {
        realRepository.delete(id);
        cache.remove(id);
    }

    public void clearCache() {
        cache.clear();
    }
}