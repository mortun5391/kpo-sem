package hse.hsebank.template;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JsonDataImporter extends DataImporter {
    private final ObjectMapper objectMapper;
    public JsonDataImporter(BankAccountFacade bankAccountFacade, CategoryFacade categoryFacade,
                            OperationFacade operationFacade) {
        super(bankAccountFacade, categoryFacade, operationFacade);
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading JSON file: " + e.getMessage(), e);
        }
        return content.toString();
    }

    @Override
    protected List<BankAccount> parseAccounts(String content) {
        try {
            Map<String, Object> data = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
            Object accountsObj = data.get("accounts");

            if (accountsObj instanceof List) {
                List<Map<String, Object>> accountsData = (List<Map<String, Object>>) accountsObj;
                List<BankAccount> accounts = new ArrayList<>();

                for (Map<String, Object> accountData : accountsData) {
                    try {
                        String id = (String) accountData.get("id");
                        String name = (String) accountData.get("name");
                        Object balanceObj = accountData.get("balance");

                        BigDecimal balance;
                        if (balanceObj instanceof Number) {
                            balance = new BigDecimal(balanceObj.toString());
                        } else if (balanceObj instanceof String) {
                            balance = new BigDecimal((String) balanceObj);
                        } else {
                            balance = BigDecimal.ZERO;
                        }

                        BankAccount account = new BankAccount(id, name, balance);
                        accounts.add(account);
                    } catch (Exception e) {
                        System.err.println("Error parsing account data: " + e.getMessage());
                    }
                }
                return accounts;
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON accounts: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    protected List<Category> parseCategories(String content) {
        try {
            Map<String, Object> data = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
            Object categoriesObj = data.get("categories");

            if (categoriesObj instanceof List) {
                List<Map<String, Object>> categoriesData = (List<Map<String, Object>>) categoriesObj;
                List<Category> categories = new ArrayList<>();

                for (Map<String, Object> categoryData : categoriesData) {
                    try {
                        String id = (String) categoryData.get("id");
                        String typeStr = (String) categoryData.get("type");
                        String name = (String) categoryData.get("name");

                        CategoryType type = CategoryType.valueOf(typeStr);
                        categories.add(new Category(id, type, name));
                    } catch (Exception e) {
                        System.err.println("Error parsing category data: " + e.getMessage());
                    }
                }
                return categories;
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON categories: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    protected List<Operation> parseOperations(String content) {
        try {
            Map<String, Object> data = objectMapper.readValue(content, new TypeReference<Map<String, Object>>() {});
            Object operationsObj = data.get("operations");

            if (operationsObj instanceof List) {
                List<Map<String, Object>> operationsData = (List<Map<String, Object>>) operationsObj;
                List<Operation> operations = new ArrayList<>();

                for (Map<String, Object> operationData : operationsData) {
                    try {
                        String id = (String) operationData.get("id");
                        String bankAccountId = (String) operationData.get("bankAccountId");
                        String categoryId = (String) operationData.get("categoryId");
                        String typeStr = (String) operationData.get("type");
                        Object amountObj = operationData.get("amount");
                        String dateStr = (String) operationData.get("date");
                        String description = (String) operationData.get("description");

                        CategoryType type = CategoryType.valueOf(typeStr);
                        BigDecimal amount;
                        if (amountObj instanceof Number) {
                            amount = new BigDecimal(amountObj.toString());
                        } else {
                            amount = new BigDecimal((String) amountObj);
                        }

                        LocalDateTime date = LocalDateTime.parse(dateStr);

                        Operation operation = new Operation(id, bankAccountId, categoryId, type, amount, date, description);
                        operations.add(operation);
                    } catch (Exception e) {
                        System.err.println("Error parsing operation data: " + e.getMessage());
                    }
                }
                return operations;
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON operations: " + e.getMessage());
        }
        return new ArrayList<>();
    }



    @Override
    public String getSupportedFormat() {
        return "JSON";
    }
}