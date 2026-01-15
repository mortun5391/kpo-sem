package hse.hsebank.services;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.facade.BankAccountFacade;
import hse.hsebank.facade.OperationFacade;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BalanceRecalculationService {
    private final BankAccountFacade bankAccountFacade;
    private final OperationFacade operationFacade;

    public BalanceRecalculationService(BankAccountFacade bankAccountFacade, OperationFacade operationFacade) {
        this.bankAccountFacade = bankAccountFacade;
        this.operationFacade = operationFacade;
    }

    public void recalculateBalance(String  accountId) {
        BankAccount account = bankAccountFacade.getAccount(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        BigDecimal newBalance = operationFacade.getOperationsByAccount(accountId).stream()
                .map(op -> op.isIncome() ? op.getAmount() : op.getAmount().negate())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        account.recalculateBalance(newBalance);
        System.out.println("Balance recalculated for account " + accountId + ": " + newBalance);
    }

    public void recalculateAllBalances() {
        bankAccountFacade.getAllAccounts().forEach(account ->
                recalculateBalance(account.getId())
        );
    }
}