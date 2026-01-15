package hse.hsebank.commands;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.facade.BankAccountFacade;
import hse.hsebank.utils.InputValidator;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountCommand implements Command {
    private final BankAccountFacade bankAccountFacade;
    private final InputValidator inputValidator;

    public CreateAccountCommand(BankAccountFacade bankAccountFacade, InputValidator inputValidator) {
        this.bankAccountFacade = bankAccountFacade;
        this.inputValidator = inputValidator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Create Bank Account ===");
        String name = inputValidator.getStringInput("Enter account name: ");

        try {
            BankAccount account = bankAccountFacade.createAccount(name);
            System.out.println("Account created successfully: " + account);
        } catch (Exception e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }
}