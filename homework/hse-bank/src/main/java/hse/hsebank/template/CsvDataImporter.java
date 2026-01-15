package hse.hsebank.template;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.facade.BankAccountFacade;
import hse.hsebank.facade.CategoryFacade;
import hse.hsebank.facade.OperationFacade;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CsvDataImporter extends DataImporter {;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public CsvDataImporter(BankAccountFacade bankAccountFacade,
                           CategoryFacade categoryFacade,
                           OperationFacade operationFacade) {
        super(bankAccountFacade, categoryFacade, operationFacade);
    }

    @Override
    protected String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file: " + e.getMessage(), e);
        }
        return content.toString();
    }

    @Override
    protected List<BankAccount> parseAccounts(String content) {
        List<BankAccount> accounts = new ArrayList<>();
        String[] lines = content.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("id,name,balance") || line.startsWith("=== ACCOUNTS ===")) {
                continue;
            }

            try {
                String[] parts = parseCsvLine(line);
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    String name = parts[1].trim();
                    BigDecimal balance = new BigDecimal(parts[2].trim());

                    BankAccount account = new BankAccount(id, name, balance);
                    accounts.add(account);
                }
            } catch (Exception e) {
                System.err.println("Error parsing account line: " + line + " - " + e.getMessage());
            }
        }

        return accounts;
    }

    @Override
    protected List<Category> parseCategories(String content) {
        List<Category> categories = new ArrayList<>();
        String[] lines = content.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("id,type,name") || line.startsWith("=== CATEGORIES ===")) {
                continue;
            }

            try {
                String[] parts = parseCsvLine(line);
                if (parts.length >= 3) {
                    String id = parts[0].trim();
                    CategoryType type = CategoryType.valueOf(parts[1].trim().toUpperCase());
                    String name = parts[2].trim();

                    Category category = new Category(id, type, name);
                    categories.add(category);
                }
            } catch (Exception e) {
                System.err.println("Error parsing category line: " + line + " - " + e.getMessage());
            }
        }

        return categories;
    }

    @Override
    protected List<Operation> parseOperations(String content) {
        List<Operation> operations = new ArrayList<>();
        String[] lines = content.split("\n");

        for (String line : lines) {
            if (line.trim().isEmpty() || line.startsWith("id,bankAccountId,categoryId,type,amount,date,description") || line.startsWith("=== OPERATIONS ===")) {
                continue;
            }

            try {
                String[] parts = parseCsvLine(line);
                if (parts.length >= 7) {
                    String id = parts[0].trim();
                    String bankAccountId = parts[1].trim();
                    String categoryId = parts[2].trim();
                    CategoryType type = CategoryType.valueOf(parts[3].trim().toUpperCase());
                    BigDecimal amount = new BigDecimal(parts[4].trim());
                    LocalDateTime date = LocalDateTime.parse(parts[5].trim(), DATE_FORMATTER);
                    String description = parts[6].trim();

                    Operation operation = new Operation(id, bankAccountId, categoryId, type, amount, date, description);
                    operations.add(operation);
                }
            } catch (Exception e) {
                System.err.println("Error parsing operation line: " + line + " - " + e.getMessage());
            }
        }

        return operations;
    }

    @Override
    public String getSupportedFormat() {
        return "CSV";
    }

    /**
     * Parse CSV line considering quoted fields and commas within quotes
     */
    String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder field = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(field.toString());
                field.setLength(0);
            } else {
                field.append(c);
            }
        }

        result.add(field.toString());
        return result.toArray(new String[0]);
    }
}