package hse.hsebank.commands;

import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.facade.OperationFacade;
import hse.hsebank.utils.InputValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class CreateOperationCommand implements Command {
    private final OperationFacade operationFacade;
    private final InputValidator inputValidator;

    public CreateOperationCommand(OperationFacade operationFacade, InputValidator inputValidator) {
        this.operationFacade = operationFacade;
        this.inputValidator = inputValidator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Create Operation ===");

        String accountId = inputValidator.getIdInput("Enter bank account ID: ");
        String categoryId = inputValidator.getIdInput("Enter category ID: ");
        CategoryType type = inputValidator.getCategoryTypeInput("Enter operation type (INCOME/OUTCOME): ");
        BigDecimal amount = inputValidator.getBigDecimalInput("Enter amount: ");
        String description = inputValidator.getStringInput("Enter description: ");

        try {
            Operation operation = operationFacade.createOperation(accountId, categoryId, type, amount, description);
            System.out.println("Operation created successfully: " + operation);
        } catch (Exception e) {
            System.out.println("Error creating operation: " + e.getMessage());
        }
    }
}