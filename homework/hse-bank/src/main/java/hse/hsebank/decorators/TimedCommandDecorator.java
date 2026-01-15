package hse.hsebank.decorators;

import hse.hsebank.commands.Command;

/**
 * Decorator for measuring command execution time
 */
public class TimedCommandDecorator implements Command {
    private final Command decoratedCommand;

    public TimedCommandDecorator(Command decoratedCommand) {
        this.decoratedCommand = decoratedCommand;
    }

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();

        try {
            decoratedCommand.execute();
        } finally {
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.printf("Command executed in %d ms%n", duration);
        }
    }
}