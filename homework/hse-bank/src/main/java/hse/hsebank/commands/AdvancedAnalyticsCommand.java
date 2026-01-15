// AdvancedAnalyticsCommand.java
package hse.hsebank.commands;

import hse.hsebank.console.ConsolePrinter;
import hse.hsebank.facade.AnalyticsFacade;
import hse.hsebank.utils.InputValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class AdvancedAnalyticsCommand implements Command {
    private final AnalyticsFacade analyticsFacade;
    private final InputValidator inputValidator;

    public AdvancedAnalyticsCommand(AnalyticsFacade analyticsFacade, InputValidator inputValidator) {
        this.analyticsFacade = analyticsFacade;
        this.inputValidator = inputValidator;
    }

    @Override
    public void execute() {
        ConsolePrinter.printSectionTitle("Advanced Analytics");

        System.out.println("1. Today's Summary");
        System.out.println("2. Current Month Summary");
        System.out.println("3. Custom Period Summary");
        System.out.println("4. Top Categories Analysis");

        int choice = inputValidator.getIntInput("Choose analytics type: ");

        switch (choice) {
            case 1 -> showTodaySummary();
            case 2 -> showCurrentMonthSummary();
            case 3 -> showCustomPeriodSummary();
            case 4 -> showTopCategoriesAnalysis();
            default -> ConsolePrinter.printError("Invalid choice");
        }
    }

    private void showTodaySummary() {
        ConsolePrinter.printSectionTitle("Today's Financial Summary");

        BigDecimal todayIncome = analyticsFacade.getTotalIncome(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().atTime(23, 59, 59)
        );

        BigDecimal todayExpenses = analyticsFacade.getTotalExpenses(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().atTime(23, 59, 59)
        );

        BigDecimal todayBalance = todayIncome.subtract(todayExpenses);

        printFinancialSummary(todayIncome, todayExpenses, todayBalance);

        showPeriodCategories(
                LocalDate.now().atStartOfDay(),
                LocalDate.now().atTime(23, 59, 59),
                "Today"
        );
    }

    private void showCurrentMonthSummary() {
        ConsolePrinter.printSectionTitle("Current Month Financial Summary");

        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().withDayOfMonth(
                LocalDate.now().lengthOfMonth()
        ).atTime(23, 59, 59);

        BigDecimal monthIncome = analyticsFacade.getTotalIncome(startOfMonth, endOfMonth);
        BigDecimal monthExpenses = analyticsFacade.getTotalExpenses(startOfMonth, endOfMonth);
        BigDecimal monthBalance = monthIncome.subtract(monthExpenses);

        printFinancialSummary(monthIncome, monthExpenses, monthBalance);

        showPeriodCategories(startOfMonth, endOfMonth, "This Month");
    }

    private void showCustomPeriodSummary() {
        ConsolePrinter.printSectionTitle("Custom Period Analysis");

        System.out.println("Enter start date (YYYY-MM-DD): ");
        String startDateStr = inputValidator.getStringInput("");
        LocalDate startDate = LocalDate.parse(startDateStr);

        System.out.println("Enter end date (YYYY-MM-DD): ");
        String endDateStr = inputValidator.getStringInput("");
        LocalDate endDate = LocalDate.parse(endDateStr);

        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atTime(23, 59, 59);

        AnalyticsFacade.FinancialSummary summary = analyticsFacade.getFinancialSummary(start, end);

        ConsolePrinter.printSectionTitle(
                String.format("Period: %s to %s",
                        startDate.format(DateTimeFormatter.ISO_DATE),
                        endDate.format(DateTimeFormatter.ISO_DATE))
        );

        printFinancialSummary(
                summary.getTotalIncome(),
                summary.getTotalExpenses(),
                summary.getNetBalance()
        );

        showPeriodCategories(start, end, "Selected Period");
    }

    private void showTopCategoriesAnalysis() {
        ConsolePrinter.printSectionTitle("Top Categories Analysis");

        int limit = inputValidator.getIntInput("Enter number of top categories to show: ");

        ConsolePrinter.printSubMenu("Top Income Categories");
        Map<String, BigDecimal> topIncome = analyticsFacade.getTopIncomeCategories(limit);
        if (topIncome.isEmpty()) {
            ConsolePrinter.printInfo("No income data available");
        } else {
            topIncome.forEach((category, amount) ->
                    System.out.printf(" %s: $%.2f%n", category, amount)
            );
        }

        ConsolePrinter.printSubMenu("Top Expense Categories");
        Map<String, BigDecimal> topExpenses = analyticsFacade.getTopSpendingCategories(limit);
        if (topExpenses.isEmpty()) {
            ConsolePrinter.printInfo("No expense data available");
        } else {
            topExpenses.forEach((category, amount) ->
                    System.out.printf(" %s: $%.2f%n", category, amount)
            );
        }
    }

    private void showPeriodCategories(LocalDateTime start, LocalDateTime end, String periodName) {
        // Income by category
        ConsolePrinter.printSubMenu(periodName + " - Income by Category");
        Map<String, BigDecimal> incomeByCategory = analyticsFacade.getIncomeByCategory(start, end);
        if (incomeByCategory.isEmpty()) {
            ConsolePrinter.printInfo("No income data for this period");
        } else {
            incomeByCategory.forEach((category, amount) ->
                    System.out.printf(" %s: $%.2f%n", category, amount)
            );
        }

        ConsolePrinter.printSubMenu(periodName + " - Expenses by Category");
        Map<String, BigDecimal> expensesByCategory = analyticsFacade.getExpensesByCategory(start, end);
        if (expensesByCategory.isEmpty()) {
            ConsolePrinter.printInfo("No expense data for this period");
        } else {
            expensesByCategory.forEach((category, amount) ->
                    System.out.printf("%s: $%.2f%n", category, amount)
            );
        }
    }

    private void printFinancialSummary(BigDecimal income, BigDecimal expenses, BigDecimal balance) {
        ConsolePrinter.printTableHeader(new String[]{"Type", "Amount"});
        ConsolePrinter.printTableRow(new String[]{"Total Income", String.format("$%.2f", income)});
        ConsolePrinter.printTableRow(new String[]{"Total Expenses", String.format("$%.2f", expenses)});

        String balanceType = balance.compareTo(BigDecimal.ZERO) >= 0 ? "Net Profit" : "Net Loss";
        String balanceDisplay = String.format("$%.2f", balance.abs());
        ConsolePrinter.printTableRow(new String[]{balanceType, balanceDisplay});
        ConsolePrinter.printSeparator();

        if (balance.compareTo(BigDecimal.ZERO) > 0) {
            ConsolePrinter.printSuccess(String.format("Positive balance: $%.2f", balance));
        } else if (balance.compareTo(BigDecimal.ZERO) < 0) {
            ConsolePrinter.printWarning(String.format("Negative balance: $%.2f", balance));
        } else {
            ConsolePrinter.printInfo("Break-even: $0.00");
        }
    }
}