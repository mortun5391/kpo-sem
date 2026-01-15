package hse.hsebank.commands;

import hse.hsebank.console.ConsolePrinter;
import hse.hsebank.domains.Operation;
import hse.hsebank.facade.OperationFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListOperationsCommand implements Command {
    private final OperationFacade operationFacade;

    public ListOperationsCommand(OperationFacade operationFacade) {
        this.operationFacade = operationFacade;
    }

    @Override
    public void execute() {
        ConsolePrinter.printSectionTitle("Operations");

        List<Operation> operations = operationFacade.getAllOperations();

        if (operations.isEmpty()) {
            ConsolePrinter.printInfo("No operations found.");
            return;
        }

        ConsolePrinter.printTableHeader(new String[]{"ID", "Type", "Amount", "Date", "Description"});

        for (Operation operation : operations) {
            String amountColor = operation.isIncome() ? "+$" : "-$";

            ConsolePrinter.printTableRow(new String[]{
                    operation.getId().substring(0, 8) + "...",
                    " " + operation.getType().getDisplayName(),
                    amountColor + String.format("%.2f", operation.getAmount()),
                    operation.getDate().toLocalDate().toString(),
                    operation.getDescription().length() > 15 ?
                            operation.getDescription().substring(0, 12) + "..." :
                            operation.getDescription()
            });
        }

        ConsolePrinter.printSeparator();
        ConsolePrinter.printSuccess("Total operations: " + operations.size());
    }
}