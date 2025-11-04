package hse.hsebank.commands;

import hse.hsebank.facade.BankAccountFacade;
import hse.hsebank.utils.InputValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateAccountCommandTest {

    @Mock
    private BankAccountFacade bankAccountFacade;

    @Mock
    private InputValidator inputValidator;

    @Test
    void testCreateAccountCommandExecution() {
        when(inputValidator.getStringInput("Enter account name: "))
                .thenReturn("Test Account");

        CreateAccountCommand command = new CreateAccountCommand(bankAccountFacade, inputValidator);

        command.execute();

        verify(inputValidator).getStringInput("Enter account name: ");
        verify(bankAccountFacade).createAccount("Test Account");
    }
}