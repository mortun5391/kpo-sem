// ConsoleMenu.java (обновленный)
package hse.hsebank.console;

import hse.hsebank.commands.Command;
import hse.hsebank.decorators.TimedCommandDecorator;
import hse.hsebank.utils.InputValidator;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Console menu manager
 */
@Component
public class ConsoleMenu {
    private final InputValidator inputValidator;
    private final Map<Integer, Command> commands = new HashMap<>();
    private boolean running = true;

    public ConsoleMenu(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public void registerCommand(int choice, Command command) {
        commands.put(choice, command);
    }

    public void run() {
        ConsolePrinter.printWelcome();

        while (running) {
            showMenu();
            int choice = inputValidator.getIntInput("Choose action: ");

            Command command = commands.get(choice);
            if (command != null) {
                Command timedCommand = new TimedCommandDecorator(command);
                timedCommand.execute();
            } else if (choice == 0) {
                running = false;
                ConsolePrinter.printGoodbye();
            } else {
                ConsolePrinter.printError("Invalid choice. Please try again.");
            }
        }
    }

    private void showMenu() {
        ConsolePrinter.printMenu(
                "Main Menu",
                new String[]{
                        "Create Bank Account",
                        "Create Category",
                        "Create Operation",
                        "Basic Analytics",
                        "Advanced Analytics",
                        "Import Data",
                        "Export Data",
                        "Recalculate Balances",
                        "List Accounts",
                        "List Categories",
                        "List Operations"
                }
        );
    }

    public void stop() {
        this.running = false;
    }
}