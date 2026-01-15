package hse.hsebank.proxy;

import hse.hsebank.domains.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BankRepository {
    void save(BankAccount account);
    Optional<BankAccount> findById(String id);
    List<BankAccount> findAll();
    void delete(String id);
}