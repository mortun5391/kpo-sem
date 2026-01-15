package hse.hsebank.facade;

import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.factories.DomainFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Component
public class OperationFacade {
    private final Map<String, Operation> operations = new HashMap<>();
    private final DomainFactory domainFactory;
    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;

    public OperationFacade(DomainFactory domainFactory,
                           BankAccountFacade bankAccountFacade,
                           CategoryFacade categoryFacade) {
        this.domainFactory = domainFactory;
        this.bankAccountFacade = bankAccountFacade;
        this.categoryFacade = categoryFacade;
    }

    public Operation createOperation(String bankAccountId, String categoryId,
                                     CategoryType type, BigDecimal amount, String description) {
        var accountOpt = bankAccountFacade.getAccount(bankAccountId);
        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Bank account not found");
        }

        var categoryOpt = categoryFacade.getCategory(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Category not found");
        }

        if (type == CategoryType.OUTCOME) {
            var account = accountOpt.get();
            if (account.getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException(
                        String.format("Insufficient funds. Current balance: %.2f, required: %.2f",
                                account.getBalance(), amount)
                );
            }
        }

        var category = categoryOpt.get();
        if (category.getType() != type) {
            throw new IllegalArgumentException(
                    String.format("Category type mismatch. Category '%s' is for %s, but operation is %s",
                            category.getName(), category.getType().getDisplayName(), type.getDisplayName())
            );
        }

        Operation operation = domainFactory.createOperation(bankAccountId, categoryId, type, amount, description);
        operations.put(operation.getId(), operation);

        bankAccountFacade.getAccount(bankAccountId).ifPresent(
                account -> account.processOperation(operation)
        );

        return operation;
    }

    public Operation createOperationWithId(String id, String bankAccountId, String categoryId,
                                           CategoryType type, BigDecimal amount, String description, LocalDateTime date) {
        var accountOpt = bankAccountFacade.getAccount(bankAccountId);
        if (accountOpt.isEmpty()) {
            throw new IllegalArgumentException("Bank account not found: " + bankAccountId);
        }

        var categoryOpt = categoryFacade.getCategory(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new IllegalArgumentException("Category not found: " + categoryId);
        }

        if (type == CategoryType.OUTCOME) {
            var account = accountOpt.get();
            if (account.getBalance().compareTo(amount) < 0) {
                throw new IllegalArgumentException(
                        String.format("Insufficient funds. Current balance: %.2f, required: %.2f",
                                account.getBalance(), amount)
                );
            }
        }

        var category = categoryOpt.get();
        if (category.getType() != type) {
            throw new IllegalArgumentException(
                    String.format("Category type mismatch. Category '%s' is for %s, but operation is %s",
                            category.getName(), category.getType().getDisplayName(), type.getDisplayName())
            );
        }

        Operation operation = new Operation(id, bankAccountId, categoryId, type, amount, date, description);
        operations.put(operation.getId(), operation);

        bankAccountFacade.getAccount(bankAccountId).ifPresent(
                account -> account.processOperation(operation)
        );

        return operation;
    }

    public Optional<Operation> getOperation(String id) {
        return Optional.ofNullable(operations.get(id));
    }

    public List<Operation> getAllOperations() {
        return new ArrayList<>(operations.values());
    }

    public List<Operation> getOperationsByAccount(String accountId) {
        return operations.values().stream()
                .filter(op -> op.getBankAccountId().equals(accountId))
                .toList();
    }

    public List<Operation> getOperationsByCategory(String categoryId) {
        return operations.values().stream()
                .filter(op -> op.getCategoryId().equals(categoryId))
                .toList();
    }

    public List<Operation> getOperationsByDateRange(LocalDateTime start, LocalDateTime end) {
        return operations.values().stream()
                .filter(op -> !op.getDate().isBefore(start) && !op.getDate().isAfter(end))
                .toList();
    }

    public boolean deleteOperation(String id) {
        return operations.remove(id) != null;
    }
}