package hse.hsebank.facade;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.factories.DomainFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class BankAccountFacade {
    private final Map<String, BankAccount> accounts = new HashMap<>();  // ← Теперь String ключ
    private final DomainFactory domainFactory;

    public BankAccountFacade(DomainFactory domainFactory) {
        this.domainFactory = domainFactory;
    }

    public BankAccount createAccount(String name) {
        BankAccount account = domainFactory.createBankAccount(name);
        accounts.put(account.getId(), account);
        return account;
    }
    public BankAccount createAccountWithId(String id, String name, BigDecimal balance) {
        BankAccount account = new BankAccount(id, name, balance);
        accounts.put(account.getId(), account);
        return account;
    }

    public Optional<BankAccount> getAccount(String id) {  // ← Теперь String параметр
        return Optional.ofNullable(accounts.get(id));
    }

    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(accounts.values());
    }

    public boolean updateAccount(String id, String newName) {  // ← Теперь String параметр
        BankAccount account = accounts.get(id);
        if (account != null) {
            account.setName(newName);
            return true;
        }
        return false;
    }

    public boolean deleteAccount(String id) {  // ← Теперь String параметр
        return accounts.remove(id) != null;
    }

    public void processOperation(BankAccount account, hse.hsebank.domains.Operation operation) {
        account.processOperation(operation);
    }


}