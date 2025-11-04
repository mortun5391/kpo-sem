package hse.hsebank.visitor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

@Component
public class YamlExportVisitor implements DataExportVisitor {
    private final ObjectMapper yamlMapper;

    public YamlExportVisitor() {
        this.yamlMapper = new ObjectMapper(new YAMLFactory());
        this.yamlMapper.registerModule(new JavaTimeModule());
        this.yamlMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        this.yamlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public String exportAccounts(List<BankAccount> accounts) {
        try {
            List<Map<String, Object>> simplifiedAccounts = new ArrayList<>();
            for (BankAccount account : accounts) {
                Map<String, Object> accMap = new LinkedHashMap<>();
                accMap.put("id", account.getId());
                accMap.put("name", account.getName());
                accMap.put("balance", account.getBalance());
                simplifiedAccounts.add(accMap);
            }

            return yamlMapper.writeValueAsString(simplifiedAccounts);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing accounts to YAML: " + e.getMessage());
            return "[]";
        }
    }

    @Override
    public String exportCategories(List<Category> categories) {
        try {
            List<Map<String, Object>> simplifiedCategories = new ArrayList<>();
            for (Category category : categories) {
                Map<String, Object> catMap = new LinkedHashMap<>();
                catMap.put("id", category.getId());
                catMap.put("type", category.getType().name());
                catMap.put("name", category.getName());
                simplifiedCategories.add(catMap);
            }

            return yamlMapper.writeValueAsString(simplifiedCategories);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing categories to YAML: " + e.getMessage());
            return "[]";
        }
    }

    @Override
    public String exportOperations(List<Operation> operations) {
        try {
            List<Map<String, Object>> simplifiedOperations = new ArrayList<>();
            for (Operation operation : operations) {
                Map<String, Object> opMap = new LinkedHashMap<>();
                opMap.put("id", operation.getId());
                opMap.put("bankAccountId", operation.getBankAccountId());
                opMap.put("categoryId", operation.getCategoryId());
                opMap.put("type", operation.getType().name());
                opMap.put("amount", operation.getAmount());
                opMap.put("date", operation.getDate().toString());
                opMap.put("description", operation.getDescription());
                simplifiedOperations.add(opMap);
            }

            return yamlMapper.writeValueAsString(simplifiedOperations);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing operations to YAML: " + e.getMessage());
            return "[]";
        }
    }

    @Override
    public String exportAll(List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        Map<String, Object> allData = new LinkedHashMap<>();
        allData.put("accounts", accounts != null ? accounts : List.of());
        allData.put("categories", categories != null ? categories : List.of());
        allData.put("operations", operations != null ? operations : List.of());
        allData.put("exportTimestamp", java.time.LocalDateTime.now().toString());
        allData.put("version", "1.0");

        try {
            return yamlMapper.writeValueAsString(allData);
        } catch (JsonProcessingException e) {
            System.err.println("Error serializing all data to YAML: " + e.getMessage());
            return "";
        }
    }

    @Override
    public boolean exportToFile(String filePath, List<BankAccount> accounts, List<Category> categories, List<Operation> operations) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Map<String, Object> allData = new LinkedHashMap<>();

            List<Map<String, Object>> simplifiedAccounts = new ArrayList<>();
            for (BankAccount account : accounts) {
                Map<String, Object> accMap = new LinkedHashMap<>();
                accMap.put("id", account.getId());
                accMap.put("name", account.getName());
                accMap.put("balance", account.getBalance());
                simplifiedAccounts.add(accMap);
            }

            List<Map<String, Object>> simplifiedCategories = new ArrayList<>();
            for (Category category : categories) {
                Map<String, Object> catMap = new LinkedHashMap<>();
                catMap.put("id", category.getId());
                catMap.put("type", category.getType().name());
                catMap.put("name", category.getName());
                simplifiedCategories.add(catMap);
            }

            List<Map<String, Object>> simplifiedOperations = getMaps(operations);

            allData.put("accounts", simplifiedAccounts);
            allData.put("categories", simplifiedCategories);
            allData.put("operations", simplifiedOperations);
            allData.put("exportTimestamp", java.time.LocalDateTime.now().toString());
            allData.put("version", "1.0");

            String yamlContent = yamlMapper.writeValueAsString(allData);
            writer.write(yamlContent);
            System.out.println("YAML data successfully exported to: " + filePath);
            return true;
        } catch (IOException e) {
            System.err.println("Error writing YAML file: " + e.getMessage());
            return false;
        }
    }

    private static List<Map<String, Object>> getMaps(List<Operation> operations) {
        List<Map<String, Object>> simplifiedOperations = new ArrayList<>();
        for (Operation operation : operations) {
            Map<String, Object> opMap = new LinkedHashMap<>();
            opMap.put("id", operation.getId());
            opMap.put("bankAccountId", operation.getBankAccountId());
            opMap.put("categoryId", operation.getCategoryId());
            opMap.put("type", operation.getType().name());
            opMap.put("amount", operation.getAmount());
            opMap.put("date", operation.getDate().toString());
            opMap.put("description", operation.getDescription());
            simplifiedOperations.add(opMap);
        }
        return simplifiedOperations;
    }

    @Override
    public String getSupportedFormat() {
        return "YAML";
    }
}