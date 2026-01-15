package hse.hsebank;

import hse.hsebank.commands.*;
import hse.hsebank.console.ConsoleMenu;
import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.facade.AnalyticsFacade;
import hse.hsebank.facade.BankAccountFacade;
import hse.hsebank.facade.CategoryFacade;
import hse.hsebank.facade.OperationFacade;
import hse.hsebank.services.BalanceRecalculationService;
import hse.hsebank.template.CsvDataImporter;
import hse.hsebank.template.JsonDataImporter;
import hse.hsebank.template.YamlDataImporter;
import hse.hsebank.template.DataImporter;
import hse.hsebank.utils.InputValidator;
import hse.hsebank.visitor.CsvExportVisitor;
import hse.hsebank.visitor.DataExportVisitor;
import hse.hsebank.visitor.JsonExportVisitor;
import hse.hsebank.visitor.YamlExportVisitor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Main console application with DI
 */
@Component
public class ConsoleApplication implements CommandLineRunner {
    private final ConsoleMenu consoleMenu;
    private final InputValidator inputValidator;
    private final CreateAccountCommand createAccountCommand;
    private final CreateCategoryCommand createCategoryCommand;
    private final CreateOperationCommand createOperationCommand;
    private final ShowAnalyticsCommand showAnalyticsCommand;
    private final AdvancedAnalyticsCommand advancedAnalyticsCommand;
    private final ListAccountsCommand listAccountsCommand;
    private final ListCategoriesCommand listCategoriesCommand;
    private final ListOperationsCommand listOperationsCommand;
    private final BalanceRecalculationService balanceRecalculationService;
    private final CsvDataImporter csvDataImporter;
    private final JsonDataImporter jsonDataImporter;
    private final YamlDataImporter yamlDataImporter;
    private final JsonExportVisitor jsonExportVisitor;
    private final CsvExportVisitor csvExportVisitor;
    private final YamlExportVisitor yamlExportVisitor;
    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;

    public ConsoleApplication(ConsoleMenu consoleMenu,
                              InputValidator inputValidator,
                              CreateAccountCommand createAccountCommand,
                              CreateCategoryCommand createCategoryCommand,
                              CreateOperationCommand createOperationCommand,
                              ShowAnalyticsCommand showAnalyticsCommand,
                              AdvancedAnalyticsCommand advancedAnalyticsCommand,
                              ListAccountsCommand listAccountsCommand,
                              ListCategoriesCommand listCategoriesCommand,
                              ListOperationsCommand listOperationsCommand,
                              BalanceRecalculationService balanceRecalculationService,
                              CsvDataImporter csvDataImporter,
                              JsonDataImporter jsonDataImporter,
                              YamlDataImporter yamlDataImporter,
                              JsonExportVisitor jsonExportVisitor,
                              CsvExportVisitor csvExportVisitor,
                              YamlExportVisitor yamlExportVisitor,
                              BankAccountFacade bankAccountFacade,
                              CategoryFacade categoryFacade,
                              OperationFacade operationFacade) {
        this.consoleMenu = consoleMenu;
        this.inputValidator = inputValidator;
        this.createAccountCommand = createAccountCommand;
        this.createCategoryCommand = createCategoryCommand;
        this.createOperationCommand = createOperationCommand;
        this.showAnalyticsCommand = showAnalyticsCommand;
        this.advancedAnalyticsCommand = advancedAnalyticsCommand;
        this.listAccountsCommand = listAccountsCommand;
        this.listCategoriesCommand = listCategoriesCommand;
        this.listOperationsCommand = listOperationsCommand;
        this.balanceRecalculationService = balanceRecalculationService;
        this.csvDataImporter = csvDataImporter;
        this.jsonDataImporter = jsonDataImporter;
        this.yamlDataImporter = yamlDataImporter;
        this.jsonExportVisitor = jsonExportVisitor;
        this.csvExportVisitor = csvExportVisitor;
        this.yamlExportVisitor = yamlExportVisitor;
        this.bankAccountFacade = bankAccountFacade;
        this.categoryFacade = categoryFacade;
        this.operationFacade = operationFacade;

        initializeMenuCommands();
    }

    private void initializeMenuCommands() {
        consoleMenu.registerCommand(1, createAccountCommand);
        consoleMenu.registerCommand(2, createCategoryCommand);
        consoleMenu.registerCommand(3, createOperationCommand);
        consoleMenu.registerCommand(4, showAnalyticsCommand);
        consoleMenu.registerCommand(5, advancedAnalyticsCommand);
        consoleMenu.registerCommand(6, this::importData);
        consoleMenu.registerCommand(7, this::exportData);
        consoleMenu.registerCommand(8, this::recalculateBalances);
        consoleMenu.registerCommand(9, listAccountsCommand);
        consoleMenu.registerCommand(10, listCategoriesCommand);
        consoleMenu.registerCommand(11, listOperationsCommand);
    }

    @Override
    public void run(String... args) {
        consoleMenu.run();
    }

    private void importData() {
        String filePath = inputValidator.getStringInput("Enter file path for import: ");

        try {
            if (!Files.exists(Paths.get(filePath))) {
                System.out.println("Error: File not found: " + filePath);
                return;
            }

            DataImporter importer = selectImporter(filePath);
            if (importer == null) {
                System.out.println("Error: Unsupported file format. Supported formats: CSV, JSON, YAML");
                return;
            }

            System.out.println("Using " + importer.getSupportedFormat() + " importer...");
            DataImporter.ImportResult result = importer.importData(filePath);

            if (result.isSuccess()) {
                System.out.println(" " + result.getMessage());
                System.out.printf("Imported: %d accounts, %d categories, %d operations%n",
                        result.getAccountsImported(),
                        result.getCategoriesImported(),
                        result.getOperationsImported());
            } else {
                System.out.println(" " + result.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error during import: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private DataImporter selectImporter(String filePath) {
        String fileName = filePath.toLowerCase();

        if (fileName.endsWith(".csv")) {
            return csvDataImporter;
        } else if (fileName.endsWith(".json")) {
            return jsonDataImporter;
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            return yamlDataImporter;
        }

        return null;
    }

    private void exportData() {
        System.out.println("Choose export format:");
        System.out.println("1. JSON");
        System.out.println("2. CSV");
        System.out.println("3. YAML");

        int formatChoice = inputValidator.getIntInput("Select format (1-3): ");

        DataExportVisitor exporter;
        String fileExtension;

        switch (formatChoice) {
            case 1:
                exporter = jsonExportVisitor;
                fileExtension = ".json";
                break;
            case 2:
                exporter = csvExportVisitor;
                fileExtension = ".csv";
                break;
            case 3:
                exporter = yamlExportVisitor;
                fileExtension = ".yaml";
                break;
            default:
                System.out.println("Error: Invalid format choice");
                return;
        }

        String filePath = inputValidator.getStringInput("Enter file path for export: ");

        if (!filePath.toLowerCase().endsWith(fileExtension)) {
            filePath += fileExtension;
        }

        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
                System.out.println("Created directory: " + parentDir.getPath());
            }

            List<BankAccount> accounts = bankAccountFacade.getAllAccounts();
            List<Category> categories = categoryFacade.getAllCategories();
            List<Operation> operations = operationFacade.getAllOperations();

            boolean success = exporter.exportToFile(filePath, accounts, categories, operations);

            if (success) {
                System.out.println("Data exported successfully to: " + filePath);
                System.out.printf("Exported: %d accounts, %d categories, %d operations%n",
                        accounts.size(), categories.size(), operations.size());
            } else {
                System.out.println("Export failed");
            }
        } catch (Exception e) {
            System.out.println("Error during export: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void recalculateBalances() {
        balanceRecalculationService.recalculateAllBalances();
        System.out.println("All balances recalculated successfully");
    }
}