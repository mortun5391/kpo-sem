package hse.hsebank.proxy;

import hse.hsebank.domains.BankAccount;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BankRepositoryImpl implements BankRepository {
    private final Map<String, BankAccount> database = new HashMap<>();

    @Override
    public void save(BankAccount account) {
        System.out.println("Saving to database: " + account.getId());
        database.put(account.getId(), account);
    }

    @Override
    public Optional<BankAccount> findById(String id) {
        System.out.println("Reading from database: " + id);
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public List<BankAccount> findAll() {
        System.out.println("Reading all accounts from database");
        return new ArrayList<>(database.values());
    }

    @Override
    public void delete(String id) {
        System.out.println("Deleting from database: " + id);
        database.remove(id);
    }
}