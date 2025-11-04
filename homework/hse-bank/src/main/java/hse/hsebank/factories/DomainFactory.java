// DomainFactory.java (обновленный)
package hse.hsebank.factories;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.utils.ShortUUID;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public interface DomainFactory {
    BankAccount createBankAccount(String name);
    Category createCategory(CategoryType type, String name);
    Operation createOperation(String bankAccountId, String categoryId, CategoryType type,
                              BigDecimal amount, String description);
}