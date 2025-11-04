package hse.hsebank.factories;

import hse.hsebank.domains.BankAccount;
import hse.hsebank.domains.Category;
import hse.hsebank.domains.Operation;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.utils.ShortUUID;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DomainFactoryImpl implements DomainFactory {

    @Override
    public BankAccount createBankAccount(String name) {
        return BankAccount.createNew(name);
    }

    @Override
    public Category createCategory(CategoryType type, String name) {
        String shortId = ShortUUID.generate();
        return new Category(shortId, type, name);
    }

    @Override
    public Operation createOperation(String bankAccountId, String categoryId, CategoryType type,
                                     BigDecimal amount, String description) {
        return Operation.createNew(bankAccountId, categoryId, type, amount, description);
    }
}