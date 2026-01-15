package hse.hsebank.template;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.facade.BankAccountFacade;
import hse.hsebank.facade.CategoryFacade;
import hse.hsebank.facade.OperationFacade;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Template method for data import
 */
public abstract class DataImporter {
    protected final BankAccountFacade bankAccountFacade;
    protected final CategoryFacade categoryFacade;
    protected final OperationFacade operationFacade;

    public DataImporter(BankAccountFacade bankAccountFacade,
                        CategoryFacade categoryFacade,
                        OperationFacade operationFacade) {
        this.bankAccountFacade = bankAccountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;
    }

    public final ImportResult importData(String filePath) {
        try {
            String content = readFile(filePath);
            List<BankAccount> accounts = parseAccounts(content);
            List<Category> categories = parseCategories(content);
            List<Operation> operations = parseOperations(content);
            saveData(accounts, categories, operations);
            postImport();

            System.out.printf("Import completed: %d accounts, %d categories, %d operations%n",
                    accounts.size(), categories.size(), operations.size());

            return new ImportResult(accounts.size(), categories.size(), operations.size(),
                    true, "Import completed successfully");
        } catch (Exception e) {
            System.err.println("Import failed: " + e.getMessage());
            return new ImportResult(0, 0, 0, false, "Import failed: " + e.getMessage());
        }
    }

    protected abstract String readFile(String filePath);
    protected abstract List<BankAccount> parseAccounts(String content);
    protected abstract List<Category> parseCategories(String content);
    protected abstract List<Operation> parseOperations(String content);

    /**
     * Common save logic for all importers
     */
    protected void saveData(List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        for (BankAccount account : accounts) {
            try {
                if (bankAccountFacade.getAccount(account.getId()).isEmpty()) {
                    BankAccount newAccount = bankAccountFacade.createAccountWithId(
                            account.getId(), account.getName(), account.getBalance()
                    );
                    System.out.println("Created account: " + account.getId() + " - " + account.getName());
                } else {
                    System.out.println("Account already exists: " + account.getId());
                }
            } catch (Exception e) {
                System.err.println("Error saving account " + account.getId() + ": " + e.getMessage());
            }
        }

        for (Category category : categories) {
            try {
                if (categoryFacade.getCategory(category.getId()).isEmpty()) {
                    Category newCategory = categoryFacade.createCategoryWithId(
                            category.getId(), category.getType(), category.getName()
                    );
                    System.out.println("Created category: " + category.getId() + " - " + category.getName());
                } else {
                    System.out.println("Category already exists: " + category.getId());
                }
            } catch (Exception e) {
                System.err.println("Error saving category " + category.getId() + ": " + e.getMessage());
            }
        }

        createMissingAccounts(accounts, operations);

        createMissingCategories(categories, operations);

        saveOperations(operations);
    }

    private void createMissingAccounts(List<BankAccount> importedAccounts, List<Operation> operations) {
        Set<String> importedAccountIds = importedAccounts.stream()
                .map(BankAccount::getId)
                .collect(Collectors.toSet());

        Set<String> referencedAccountIds = operations.stream()
                .map(Operation::getBankAccountId)
                .collect(Collectors.toSet());

        for (String accountId : referencedAccountIds) {
            if (!importedAccountIds.contains(accountId) && bankAccountFacade.getAccount(accountId).isEmpty()) {
                try {
                    BankAccount newAccount = bankAccountFacade.createAccountWithId(
                            accountId, "Imported Account " + accountId, BigDecimal.ZERO
                    );
                    System.out.println("Created missing account: " + accountId);
                } catch (Exception e) {
                    System.err.println("Error creating missing account " + accountId + ": " + e.getMessage());
                }
            }
        }
    }

    private void createMissingCategories(List<Category> importedCategories, List<Operation> operations) {
        Set<String> importedCategoryIds = importedCategories.stream()
                .map(Category::getId)
                .collect(Collectors.toSet());

        Set<String> referencedCategoryIds = operations.stream()
                .map(Operation::getCategoryId)
                .collect(Collectors.toSet());

        for (String categoryId : referencedCategoryIds) {
            if (!importedCategoryIds.contains(categoryId) && categoryFacade.getCategory(categoryId).isEmpty()) {
                try {
                    Category newCategory = categoryFacade.createCategoryWithId(
                            categoryId, CategoryType.OUTCOME, "Imported Category " + categoryId
                    );
                    System.out.println("Created missing category: " + categoryId);
                } catch (Exception e) {
                    System.err.println("Error creating missing category " + categoryId + ": " + e.getMessage());
                }
            }
        }
    }

    private void saveOperations(List<Operation> operations) {
        int successfulOperations = 0;
        int skippedOperations = 0;

        for (Operation operation : operations) {
            try {
                if (operationFacade.getOperation(operation.getId()).isEmpty()) {
                    Operation newOperation = operationFacade.createOperationWithId(
                            operation.getId(),
                            operation.getBankAccountId(),
                            operation.getCategoryId(),
                            operation.getType(),
                            operation.getAmount(),
                            operation.getDescription(),
                            operation.getDate()
                    );
                    successfulOperations++;
                    System.out.println("Created operation: " + operation.getId());
                } else {
                    System.out.println("Operation already exists: " + operation.getId());
                    skippedOperations++;
                }
            } catch (Exception e) {
                System.err.println("Error creating operation " + operation.getId() + ": " + e.getMessage());
                skippedOperations++;
            }
        }

        System.out.println("Operations: " + successfulOperations + " created, " + skippedOperations + " skipped");
    }

    protected void postImport() {
        System.out.println("Import completed successfully");
    }

    public abstract String getSupportedFormat();

    public static class ImportResult {
        private final int accountsImported;
        private final int categoriesImported;
        private final int operationsImported;
        private final boolean success;
        private final String message;

        public ImportResult(int accountsImported, int categoriesImported, int operationsImported, boolean success, String message) {
            this.accountsImported = accountsImported;
            this.categoriesImported = categoriesImported;
            this.operationsImported = operationsImported;
            this.success = success;
            this.message = message;
        }

        // Getters
        public int getAccountsImported() { return accountsImported; }
        public int getCategoriesImported() { return categoriesImported; }
        public int getOperationsImported() { return operationsImported; }
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}