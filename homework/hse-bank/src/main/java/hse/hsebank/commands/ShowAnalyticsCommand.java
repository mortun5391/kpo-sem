// ShowAnalyticsCommand.java
package hse.hsebank.commands;

import hse.hsebank.facade.AnalyticsFacade;
import hse.hsebank.facade.BankAccountFacade;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ShowAnalyticsCommand implements Command {
    private final AnalyticsFacade analyticsFacade;

    public ShowAnalyticsCommand(AnalyticsFacade analyticsFacade) {
        this.analyticsFacade = analyticsFacade;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Financial Analytics ===");

        BigDecimal totalIncome = analyticsFacade.getTotalIncome();
        BigDecimal totalExpenses = analyticsFacade.getTotalExpenses();
        BigDecimal netBalance = totalIncome.subtract(totalExpenses);

        System.out.printf("Total Income: $%.2f%n", totalIncome);
        System.out.printf("Total Expenses: $%.2f%n", totalExpenses);
        System.out.printf("Net Balance: $%.2f%n", netBalance);

        System.out.println("\n=== Income by Category ===");
        analyticsFacade.getIncomeByCategory().forEach((category, amount) ->
                System.out.printf("%s: $%.2f%n", category, amount)
        );

        System.out.println("\n=== Expenses by Category ===");
        analyticsFacade.getExpensesByCategory().forEach((category, amount) ->
                System.out.printf("%s: $%.2f%n", category, amount)
        );
    }
}