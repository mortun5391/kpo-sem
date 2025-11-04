package hse.hsebank.commands;

import hse.hsebank.console.ConsolePrinter;
import hse.hsebank.domains.BankAccount;
import hse.hsebank.facade.BankAccountFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListAccountsCommand implements Command {
    private final BankAccountFacade bankAccountFacade;

    public ListAccountsCommand(BankAccountFacade bankAccountFacade) {
        this.bankAccountFacade = bankAccountFacade;
    }

    @Override
    public void execute() {
        ConsolePrinter.printSectionTitle("Bank Accounts");

        List<BankAccount> accounts = bankAccountFacade.getAllAccounts();

        if (accounts.isEmpty()) {
            ConsolePrinter.printInfo("No accounts found.");
            return;
        }

        ConsolePrinter.printTableHeader(new String[]{"ID", "Name", "Balance"});

        for (BankAccount account : accounts) {
            ConsolePrinter.printTableRow(new String[]{
                    account.getId().substring(0, 8) + "...",
                    account.getName(),
                    String.format("$%.2f", account.getBalance())
            });
        }

        ConsolePrinter.printSeparator();
        ConsolePrinter.printSuccess("Total accounts: " + accounts.size());
    }
}