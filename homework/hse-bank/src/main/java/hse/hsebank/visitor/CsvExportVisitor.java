package hse.hsebank.visitor;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvExportVisitor implements DataExportVisitor {

    @Override
    public String exportAccounts(List<BankAccount> accounts) {
        StringBuilder csv = new StringBuilder();
        csv.append("id,name,balance\n");

        for (BankAccount account : accounts) {
            csv.append(String.format("%s,%s,%.2f\n",
                    account.getId(),
                    escapeCsv(account.getName()),
                    account.getBalance()));
        }

        return csv.toString();
    }

    @Override
    public String exportCategories(List<Category> categories) {
        StringBuilder csv = new StringBuilder();
        csv.append("id,type,name\n");

        for (Category category : categories) {
            csv.append(String.format("%s,%s,%s\n",
                    category.getId(),
                    category.getType().name(),
                    escapeCsv(category.getName())));
        }

        return csv.toString();
    }

    @Override
    public String exportOperations(List<Operation> operations) {
        StringBuilder csv = new StringBuilder();
        csv.append("id,bankAccountId,categoryId,type,amount,date,description\n");

        for (Operation operation : operations) {
            csv.append(String.format("%s,%s,%s,%s,%.2f,%s,%s\n",
                    operation.getId(),
                    operation.getBankAccountId(),
                    operation.getCategoryId(),
                    operation.getType().name(),
                    operation.getAmount(),
                    operation.getDate().toString(),
                    escapeCsv(operation.getDescription())));
        }

        return csv.toString();
    }

    @Override
    public String exportAll(List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        StringBuilder csv = new StringBuilder();
        csv.append("=== ACCOUNTS ===\n");
        csv.append(exportAccounts(accounts));
        csv.append("\n=== CATEGORIES ===\n");
        csv.append(exportCategories(categories));
        csv.append("\n=== OPERATIONS ===\n");
        csv.append(exportOperations(operations));

        return csv.toString();
    }

    @Override
    public boolean exportToFile(String filePath, List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String csvContent = exportAll(accounts, categories, operations);
            writer.write(csvContent);
            System.out.println("CSV data successfully exported to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            return false;
        }
    }

    @Override
    public String getSupportedFormat() {
        return "CSV";
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}