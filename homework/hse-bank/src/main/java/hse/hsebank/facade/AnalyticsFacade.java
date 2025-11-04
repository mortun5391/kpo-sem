// AnalyticsFacade.java
package hse.hsebank.facade;

import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AnalyticsFacade {
    private final OperationFacade operationFacade;
    private final CategoryFacade categoryFacade;

    public AnalyticsFacade(OperationFacade operationFacade, CategoryFacade categoryFacade) {
        this.operationFacade = operationFacade;
        this.categoryFacade = categoryFacade;
    }

    /**
     * Calculate income/expense difference for selected period
     */
    public BigDecimal calculateBalanceDifference(LocalDateTime start, LocalDateTime end) {
        List<Operation> operations = operationFacade.getOperationsByDateRange(start, end);

        BigDecimal totalIncome = operations.stream()
                .filter(Operation::isIncome)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpenses = operations.stream()
                .filter(Operation::isExpense)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalIncome.subtract(totalExpenses);
    }

    /**
     * Calculate balance difference for today
     */
    public BigDecimal calculateTodayBalanceDifference() {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(23, 59, 59);
        return calculateBalanceDifference(startOfDay, endOfDay);
    }

    /**
     * Calculate balance difference for current month
     */
    public BigDecimal calculateCurrentMonthBalanceDifference() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endOfMonth = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth()).atTime(23, 59, 59);
        return calculateBalanceDifference(startOfMonth, endOfMonth);
    }

    /**
     * Group operations by category for specific type
     */
    public Map<String, BigDecimal> groupOperationsByCategory(CategoryType type) {
        return operationFacade.getAllOperations().stream()
                .filter(op -> op.getType() == type)
                .collect(Collectors.groupingBy(
                        op -> categoryFacade.getCategory(op.getCategoryId())
                                .map(Category::getName)
                                .orElse("Unknown"),
                        Collectors.reducing(BigDecimal.ZERO, Operation::getAmount, BigDecimal::add)
                ));
    }

    /**
     * Group operations by category for specific period
     */
    public Map<String, BigDecimal> groupOperationsByCategory(CategoryType type, LocalDateTime start, LocalDateTime end) {
        return operationFacade.getOperationsByDateRange(start, end).stream()
                .filter(op -> op.getType() == type)
                .collect(Collectors.groupingBy(
                        op -> categoryFacade.getCategory(op.getCategoryId())
                                .map(Category::getName)
                                .orElse("Unknown"),
                        Collectors.reducing(BigDecimal.ZERO, Operation::getAmount, BigDecimal::add)
                ));
    }

    public Map<String, BigDecimal> getIncomeByCategory() {
        return groupOperationsByCategory(CategoryType.INCOME);
    }

    public Map<String, BigDecimal> getExpensesByCategory() {
        return groupOperationsByCategory(CategoryType.OUTCOME);
    }

    /**
     * Get income by category for specific period
     */
    public Map<String, BigDecimal> getIncomeByCategory(LocalDateTime start, LocalDateTime end) {
        return groupOperationsByCategory(CategoryType.INCOME, start, end);
    }

    /**
     * Get expenses by category for specific period
     */
    public Map<String, BigDecimal> getExpensesByCategory(LocalDateTime start, LocalDateTime end) {
        return groupOperationsByCategory(CategoryType.OUTCOME, start, end);
    }

    public BigDecimal getTotalIncome() {
        return operationFacade.getAllOperations().stream()
                .filter(Operation::isIncome)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalExpenses() {
        return operationFacade.getAllOperations().stream()
                .filter(Operation::isExpense)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get total income for specific period
     */
    public BigDecimal getTotalIncome(LocalDateTime start, LocalDateTime end) {
        return operationFacade.getOperationsByDateRange(start, end).stream()
                .filter(Operation::isIncome)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get total expenses for specific period
     */
    public BigDecimal getTotalExpenses(LocalDateTime start, LocalDateTime end) {
        return operationFacade.getOperationsByDateRange(start, end).stream()
                .filter(Operation::isExpense)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCurrentBalance(String accountId) {
        return operationFacade.getOperationsByAccount(accountId).stream()
                .map(op -> op.isIncome() ? op.getAmount() : op.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get financial summary for period
     */
    public FinancialSummary getFinancialSummary(LocalDateTime start, LocalDateTime end) {
        BigDecimal income = getTotalIncome(start, end);
        BigDecimal expenses = getTotalExpenses(start, end);
        BigDecimal balance = income.subtract(expenses);

        return new FinancialSummary(income, expenses, balance);
    }

    /**
     * Get top spending categories
     */
    public Map<String, BigDecimal> getTopSpendingCategories(int limit) {
        return getExpensesByCategory().entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Get top income categories
     */
    public Map<String, BigDecimal> getTopIncomeCategories(int limit) {
        return getIncomeByCategory().entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * Financial summary data class
     */
    public static class FinancialSummary {
        private final BigDecimal totalIncome;
        private final BigDecimal totalExpenses;
        private final BigDecimal netBalance;

        public FinancialSummary(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal netBalance) {
            this.totalIncome = totalIncome;
            this.totalExpenses = totalExpenses;
            this.netBalance = netBalance;
        }

        public BigDecimal getTotalIncome() { return totalIncome; }
        public BigDecimal getTotalExpenses() { return totalExpenses; }
        public BigDecimal getNetBalance() { return netBalance; }
    }
}