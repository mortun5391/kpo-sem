package hse.hsebank.visitor;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;

import java.util.List;

/**
 * Visitor interface for data export
 */
public interface DataExportVisitor {
    String exportAccounts(List<BankAccount> accounts);
    String exportCategories(List<Category> categories);
    String exportOperations(List<Operation> operations);
    String exportAll(List<BankAccount> accounts, List<Category> categories, List<Operation> operations);

    boolean exportToFile(String filePath, List<BankAccount> accounts, List<Category> categories, List<Operation> operations);
    String getSupportedFormat();
}